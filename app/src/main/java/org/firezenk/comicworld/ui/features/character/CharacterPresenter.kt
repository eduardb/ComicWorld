package org.firezenk.comicworld.ui.features.character

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.firezenk.comicworld.ui.features.commons.Presenter
import org.firezenk.comicworld.ui.features.commons.Screen
import org.firezenk.kartographer.library.Kartographer
import javax.inject.Inject

class CharacterPresenter @Inject constructor(router: Kartographer, private val states: CharacterStates)
    : Presenter<Actions, CharacterStates>(router) {

    private lateinit var id: String

    override fun init(screen: Screen<CharacterStates>) {
        id = router.payload<String>("id")!!
        super.init(screen)
    }

    override fun reduce(action: Actions) {
        when (action) {
            is LoadCharacter -> launch(UI) {
                val model = action.getCharacter.execute(id).toEither()
                model.fold({
                    render(states.error())
                }, {
                    render(states.success(it))
                })
            }
            is Back -> router.backOnPath { action.block }
        }
    }
}