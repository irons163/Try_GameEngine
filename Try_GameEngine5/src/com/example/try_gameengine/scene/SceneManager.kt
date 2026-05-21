package com.example.try_gameengine.scene

import android.content.Context
import com.example.try_gameengine.framework.LayerManager
import java.lang.reflect.InvocationTargetException

class SceneManager private constructor() {
    val scenes: MutableList<Scene> = ArrayList<Scene>()
    var currentActiveScene: Scene? = null
        private set
    private var currentSceneIndex = 0
    private var sceneClassMap: MutableMap<SceneClassInfo?, Class<out Scene?>?>? = null
    private var nextSceneIndexForAdd = currentSceneIndex

    internal inner class SceneClassInfo {
        var context: Context? = null
        var id: String? = null
        var sceneLayerLevel: Int = -1
        var mode: Int = -1
        private val obj: Any? = null
    }

    private object SceneManagerHolder {
        var instance: SceneManager = SceneManager()
            get() = field
    }

    @Deprecated("")
    fun addScene(scene: Scene) {
        scenes.add(scene)
        if (scene.sceneLayerLevel < 0) {
            LayerManager.Companion.getInstance().setLayerBySenceIndex(nextSceneIndexForAdd)
            scene.setLayerLevel(nextSceneIndexForAdd)
            nextSceneIndexForAdd = nextSceneIndexForAdd + 1
        } else {
            nextSceneIndexForAdd = scene.getLayerLevel() + 1
        }
    }

    fun addScene(sceneBuilder: SceneBuilder) {
        val sceneIndex = sceneBuilder.getSceneIndex()
        val scene: Scene
        if (sceneIndex < 0) {
            LayerManager.Companion.getInstance().setLayerBySenceIndex(nextSceneIndexForAdd)
            //			scene.setLayerLevel(nextSceneIndexForAdd);
            scene = sceneBuilder.createScene(nextSceneIndexForAdd)!!
            scene.setLayerLevel(nextSceneIndexForAdd)
            nextSceneIndexForAdd = nextSceneIndexForAdd + 1
        } else {
            LayerManager.Companion.getInstance().setLayerBySenceIndex(sceneIndex)
            scene = sceneBuilder.createScene(sceneIndex)!!
            scene.setLayerLevel(sceneIndex)
            nextSceneIndexForAdd = sceneIndex + 1
        }

        scenes.add(scene)
    }

    @JvmOverloads
    fun addScene(
        sceneClass: Class<out Scene?>?,
        context: Context?,
        id: String?,
        sceneLayerLevel: Int = nextSceneIndexForAdd,
        mode: Int = Scene.Companion.RESTART
    ) {
        if (sceneClassMap == null) sceneClassMap = HashMap<SceneClassInfo?, Class<out Scene?>?>()
        val sceneClassInfo = SceneClassInfo()
        sceneClassInfo.context = context
        sceneClassInfo.id = id
        sceneClassInfo.sceneLayerLevel = sceneLayerLevel
        sceneClassInfo.mode = mode
        sceneClassMap!!.put(sceneClassInfo, sceneClass)
        nextSceneIndexForAdd = sceneLayerLevel + 1
    }

    fun getScene(id: String?): Scene? {
        var targetScene: Scene? = null
        for (i in scenes.indices) {
            val scene = scenes.get(i)
            if (scene.id != null && scene.id == id) {
                targetScene = scene
            }
        }

        return targetScene
    }

    fun getSceneAt(index: Int): Scene? {
        var targetScene: Scene? = null
        for (i in scenes.indices) {
            val scene = scenes.get(i)
            if (scene.getLayerLevel() == index) {
                targetScene = scene
                break
            }
        }

        return targetScene
    }

    fun getSceneIndex(id: String?): Int {
        var targetSceneIndex = -1
        for (i in scenes.indices) {
            val scene = scenes.get(i)
            if (scene.id != null && scene.id == id) {
                targetSceneIndex = scene.getLayerLevel()
            }
        }

        return targetSceneIndex
    }

    private fun createScene(index: Int): Scene? {
        var sceneClassForStart: MutableMap.MutableEntry<SceneClassInfo?, Class<out Scene?>?>? = null
        var scene: Scene? = null
        if (sceneClassMap != null) for (sceneClass in sceneClassMap!!.entries) {
            if (index == sceneClass.key!!.sceneLayerLevel) {
                val context = sceneClass.key!!.context
                val id = sceneClass.key!!.id
                val sceneLayerLevel = sceneClass.key!!.sceneLayerLevel
                val mode = sceneClass.key!!.mode

                LayerManager.Companion.getInstance().setLayerBySenceIndex(index)

                for (i in 0..2) {
                    try {
                        if (i == 0) {
                            scene = sceneClass.value!!.getConstructor(
                                Context::class.java,
                                String::class.java,
                                Int::class.javaPrimitiveType,
                                Int::class.javaPrimitiveType
                            ).newInstance(context, id, sceneLayerLevel, mode)
                        } else if (i == 1) {
                            scene = sceneClass.value!!.getConstructor(
                                Context::class.java,
                                String::class.java,
                                Int::class.javaPrimitiveType
                            ).newInstance(context, id, sceneLayerLevel)
                        } else {
                            scene = sceneClass.value!!.getConstructor(
                                Context::class.java,
                                String::class.java
                            ).newInstance(context, id)
                            if (scene != null) scene.setLayerLevel(sceneLayerLevel)
                        }
                    } catch (e: InstantiationException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    } catch (e: IllegalAccessException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    } catch (e: IllegalArgumentException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    } catch (e: InvocationTargetException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    } catch (e: NoSuchMethodException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }

                    if (scene != null) break
                }

                if (scene == null) throw RuntimeException()
                scenes.add(scene)
                sceneClassForStart = sceneClass
            }
        }
        if (sceneClassForStart != null) sceneClassMap!!.remove(sceneClassForStart.key)

        if (scene == null) {
            for (sceneExist in scenes) {
                if (index == sceneExist.getLayerLevel()) {
                    scene = sceneExist
                    break
                }
            }
        }

        return scene
    }

    fun startScene(id: String?) {
        if (currentActiveScene != null) currentActiveScene!!.stop()
        val index = getSceneIndex(id)
        startScene(index)
    }

    fun stopScene(id: String?) {
        val scene = getScene(id)
        if (scene != null) {
            scene.stop()
        }
    }

    @JvmOverloads
    fun startScene(index: Int, objForSendToScene: Any? = null): Boolean {
        var isNeedStopCurrentActiveScene = true
        val scene = createScene(index)

        if (scene == null) return false

        if (scene is DialogScene) {
            isNeedStopCurrentActiveScene = scene.isNeedToStopTheActiveScene
        }

        if (currentActiveScene != null) {
            if (isNeedStopCurrentActiveScene) {
                currentActiveScene!!.stop()
                currentActiveScene!!.addMode(Scene.Companion.BLOCK)
            }
        }

        LayerManager.Companion.getInstance().setLayerBySenceIndex(index)
        scene.startWithObj(objForSendToScene)
        currentActiveScene = scene
        currentSceneIndex = index

        return true
    }

    fun startLastScene() {
        startScene(scenes.size - 1)
    }

    fun startLastScene(objForSendToScene: Any?) {
        startScene(scenes.size - 1, objForSendToScene)
    }

    fun stopScene(index: Int) {
        if (index >= 0 && index < scenes.size) {
            scenes.get(index).stop()
        }
    }

    /*
	 * Previous.
	 // */
    /**
     * @return if false, there is not previous scene. The current scene is the first scene in scene manager.
     * Otherwise, return true.
     // */
    @JvmOverloads
    fun previousWithExistedScenes(objForSendToScene: Any? = null): Boolean {
        if (currentSceneIndex == 0) {
            return false
        } else {
            return previousWithExistedScenesCycle(false, objForSendToScene)
        }
    }

    //	public boolean previousWithExistedScenes(){
    //		if(currentSceneIndex==0){
    //			if(currentActiveScene!=null){
    //				currentActiveScene.stop();
    //				scenes.remove(currentActiveScene);
    //				currentActiveScene.finish();
    //				currentActiveScene = null;
    //			}
    //			return false;
    //		}else{
    //			previousWithExistedScenesCycle();
    //			return true;
    //		}
    //	}
    @JvmOverloads
    fun previousWithExistedScenesCycle(
        isCycle: Boolean = true,
        objForSendToScene: Any? = null
    ): Boolean {
        var currentActiveSceneOrderInScenes = 0

        var isFind = false
        for (i in scenes.indices) {
            if (scenes.get(i) == currentActiveScene) {
                currentActiveSceneOrderInScenes = i
                isFind = true
                break
            }
        }

        if (!isFind) throw RuntimeException("The current scene is not exist in the exsitedScenes.")

        currentActiveSceneOrderInScenes--

        if (currentActiveScene != null) {
            currentActiveScene!!.stop()
            val activeScene = currentActiveScene
            if (activeScene is DialogScene) activeScene.finish()
        }
        if (currentActiveSceneOrderInScenes == -1) {
            if (isCycle) currentActiveSceneOrderInScenes = scenes.size - 1
            else return false
        }
        val scene = scenes.get(currentActiveSceneOrderInScenes)
        //		if(!(scene instanceof DialogScene)){
        LayerManager.Companion.getInstance().setLayerBySenceIndex(scene.getLayerLevel())
        //		}
        if (currentActiveScene is DialogScene) {
            val savedMode = scene.getMode()
            scene.setMode(Scene.Companion.RESUME_WITHOUT_SET_VIEW)
            scene.startWithObj(objForSendToScene)
            scene.setMode(savedMode)
            scene.removeMode(Scene.Companion.BLOCK)
        } else {
            scene.startWithObj(objForSendToScene)
        }

        currentActiveScene = scene
        currentSceneIndex = scene.getLayerLevel()

        return true
    }

    /*
	 * Next.
	 // */
    @JvmOverloads
    fun nextWithExistedScenes(objForSendToScene: Any? = null): Boolean {
        if (currentSceneIndex == scenes.size - 1) {
            return false
        } else {
            nextWithExistedScenesCycle(false, objForSendToScene)
            return true
        }
    }

    @JvmOverloads
    fun nextWithExistedScenesCycle(
        isCycle: Boolean = true,
        objForSendToScene: Any? = null
    ): Boolean {
        var currentActiveSceneOrderInScenes = 0

        var isFind = false
        for (i in scenes.indices) {
            if (scenes.get(i) == currentActiveScene) {
                currentActiveSceneOrderInScenes = i
                isFind = true
                break
            }
        }

        if (!isFind) throw RuntimeException("The current scene is not exist in the exsitedScenes.")

        currentActiveSceneOrderInScenes++
        if (currentActiveSceneOrderInScenes == scenes.size) {
            if (isCycle) currentActiveSceneOrderInScenes = 0
            else return false
        }

        val scene = scenes.get(currentActiveSceneOrderInScenes)

        var isNeedStopCurrentActiveScene = true
        if (scene is DialogScene) {
            isNeedStopCurrentActiveScene = scene.isNeedToStopTheActiveScene
        }

        if (currentActiveScene != null) {
            if (isNeedStopCurrentActiveScene) {
                currentActiveScene!!.stop()
                currentActiveScene!!.addMode(Scene.Companion.BLOCK)
            }
        }

        LayerManager.Companion.getInstance().setLayerBySenceIndex(scene.getLayerLevel())

        scene.startWithObj(objForSendToScene)
        currentActiveScene = scene
        currentSceneIndex = scene.getLayerLevel()

        return true
    }

    /*
	 * Start Scene.
	 // */
    @JvmOverloads
    fun startNextScene(objForSendToScene: Any? = null): Boolean {
        if (currentSceneIndex == nextSceneIndexForAdd - 1) {
            return false
        } else {
            if (startNextSceneWithCycle(false, objForSendToScene)) return true
            else {
                if (currentActiveScene != null) {
                    currentActiveScene!!.stop()
                    scenes.remove(currentActiveScene)
                    currentActiveScene!!.finish()
                    currentActiveScene = null
                }
                return false
            }
        }
    }

    fun startNextSceneWithCycle(isCycle: Boolean, objForSendToScene: Any?): Boolean {
        var scene: Scene? = null
        for (i in 0..<nextSceneIndexForAdd) {
            currentSceneIndex++
            if (currentSceneIndex == nextSceneIndexForAdd) {
                if (!isCycle) {
                    currentSceneIndex = nextSceneIndexForAdd - 1
                    break
                }
                currentSceneIndex = 0
            }

            scene = createScene(currentSceneIndex)

            if (scene != null) break
        }

        if (scene == null) return false

        if (currentActiveScene != null) {
            currentActiveScene!!.stop()
            val activeScene = currentActiveScene
            if (activeScene is DialogScene) activeScene.finish()
        }

        LayerManager.Companion.getInstance().setLayerBySenceIndex(currentSceneIndex)
        scene.startWithObj(objForSendToScene)
        currentActiveScene = scene

        return true
    }

    fun startPreviousSceneWithCycle(isCycle: Boolean): Boolean {
        if (currentActiveScene != null) {
            currentActiveScene!!.stop()
            val activeScene = currentActiveScene
            if (activeScene is DialogScene) activeScene.finish()
        }

        var scene: Scene? = null
        for (i in 0..<nextSceneIndexForAdd) {
            currentSceneIndex--
            if (currentSceneIndex == -1) {
                if (!isCycle) {
                    currentSceneIndex = 0
                    break
                }
                currentSceneIndex = nextSceneIndexForAdd - 1
            }

            scene = createScene(currentSceneIndex)

            if (scene != null) break
        }

        if (scene == null) return false

        LayerManager.Companion.getInstance().setLayerBySenceIndex(currentSceneIndex)

        if (currentActiveScene is DialogScene) {
            val savedMode = scene.getMode()
            scene.setMode(Scene.Companion.RESUME_WITHOUT_SET_VIEW)
            scene.start()
            scene.setMode(savedMode)
            scene.removeMode(Scene.Companion.BLOCK)
        } else {
            scene.start()
        }

        currentActiveScene = scene

        return true
    }

    fun startPreviousScene(): Boolean {
        if (currentSceneIndex == 0) {
            if (currentActiveScene != null) {
                currentActiveScene!!.stop()
                scenes.remove(currentActiveScene)
                currentActiveScene!!.finish()
                currentActiveScene = null
            }
            return false
        } else {
            if (startPreviousSceneWithCycle(false)) return true
            else {
                if (currentActiveScene != null) {
                    currentActiveScene!!.stop()
                    scenes.remove(currentActiveScene)
                    currentActiveScene!!.finish()
                    currentActiveScene = null
                }
                return false
            }
        }
    }

    fun stopAllScenes() {
        for (scene in scenes) {
            scene.stop()
        }
    }

    fun removeScene(scene: Scene) {
        scenes.remove(scene)
        scene.finish()
    }

    fun removeScene(index: Int) {
        scenes.removeAt(index).finish()
    }

    //remove scene but not destroy, if you want add it back.
    fun removeSceneButNotDestroy(scene: Scene?) {
        scenes.remove(scene)
    }

    fun removeSceneButNotDestroy(index: Int) {
        scenes.removeAt(index)
    }

    fun removeAllScenes() {
        for (scene in scenes) {
            scene.finish()
        }
        scenes.clear()
    }

    fun reset() {
        scenes.clear()
        currentActiveScene = null
        currentSceneIndex = 0
        sceneClassMap!!.clear()
        sceneClassMap = null
        nextSceneIndexForAdd = currentSceneIndex
    }

    companion object {
        fun getInstance(): SceneManager {
            return SceneManagerHolder.instance
        }
    }
}
