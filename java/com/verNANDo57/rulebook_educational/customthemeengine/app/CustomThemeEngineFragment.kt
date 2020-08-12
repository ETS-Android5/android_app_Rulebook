package com.verNANDo57.rulebook_educational.customthemeengine.app

import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import com.verNANDo57.rulebook_educational.customthemeengine.CustomThemeEngine

/**
 * Base class for fragments[Fragment] that use [CustomThemeEngine] for dynamic themes.
 */
open class CustomThemeEngineFragment : Fragment() {

  /**
   * The [CustomThemeEngine] instance used for styling.
   */
  open val customThemeEngine: CustomThemeEngine get() = (activity as? BaseCustomThemeEngineActivity)?.customThemeEngine ?: CustomThemeEngine.INSTANCE

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    super.onCreateOptionsMenu(menu, inflater)
  }
}
