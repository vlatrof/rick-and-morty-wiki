package com.vlatrof.rickandmorty.presentation.screen.feature

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vlatrof.rickandmorty.R
import com.vlatrof.rickandmorty.presentation.common.navigation.BackStack
import com.vlatrof.rickandmorty.presentation.common.navigation.NavigationManager
import com.vlatrof.rickandmorty.presentation.common.util.ClassUtils
import com.vlatrof.rickandmorty.presentation.screen.base.BackStackRootFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.characterdetails.CharacterDetailsFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.episodedetails.EpisodeDetailsFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.locationdetails.LocationDetailsFragment

class MainActivity : AppCompatActivity(), NavigationManager {

    private lateinit var currentBackStack: BackStack

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RickAndMorty)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
        initBackStack(savedInstanceState)
    }

    override fun launchCharacterDetailsFragment(characterId: Int) {
        launchFragment(CharacterDetailsFragment.newInstance(characterId))
    }

    override fun launchLocationDetailsFragment(locationId: Int) {
        launchFragment(LocationDetailsFragment.newInstance(locationId))
    }

    override fun launchEpisodeDetailsFragment(episodeId: Int) {
        launchFragment(EpisodeDetailsFragment.newInstance(episodeId))
    }

    override fun goBack() {
        supportFragmentManager.popBackStack()
    }

    private fun launchFragment(fragment: Fragment, tag: String? = null) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment_container, fragment, tag)
            addToBackStack(currentBackStack.tag)
        }
    }

    private fun initBackStack(savedState: Bundle?) {
        savedState?.getString(SAVED_INSTANCE_STATE_BACKSTACK_KEY)?.let { restoredTag ->
            BackStack.getByTag(restoredTag)?.let {
                currentBackStack = it
                return
            }
        }

        currentBackStack = BackStack.default
        launchFragment(currentBackStack.createRootFragmentInstance())
    }

    private fun switchBackStack(newBackStack: BackStack) {
        supportFragmentManager.saveBackStack(currentBackStack.tag)
        supportFragmentManager.restoreBackStack(newBackStack.tag)
        currentBackStack = newBackStack
    }

    private fun setupNavigation() {
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                launchFragment(currentBackStack.createRootFragmentInstance())
            }
        }

        findViewById<BottomNavigationView>(R.id.bottom_navigation_view).apply {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.characters -> switchBackStack(BackStack.Characters)
                    R.id.locations -> switchBackStack(BackStack.Locations)
                    R.id.episodes -> switchBackStack(BackStack.Episodes)
                }
                true
            }
            setOnItemReselectedListener {
                onMenuItemReselected()
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 1) goBack() else finish()
        }
    }

    private fun onMenuItemReselected() {
        supportFragmentManager.apply {
            if (backStackEntryCount == 1) {
                findFragmentById(R.id.main_fragment_container)?.let {
                    if (it is BackStackRootFragment) it.onTabReselected()
                }
            }
            if (backStackEntryCount > 1) {
                repeat(backStackEntryCount - 1) { popBackStack() }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_INSTANCE_STATE_BACKSTACK_KEY, currentBackStack.tag)
    }

    companion object {
        private val SAVED_INSTANCE_STATE_BACKSTACK_KEY =
            ClassUtils.generateTag(BackStack::class.java)
    }
}
