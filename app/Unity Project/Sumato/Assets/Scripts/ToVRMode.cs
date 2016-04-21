using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;

public class ToVRMode : MonoBehaviour {

	public string levelToLoad = "VRScreen";



	public void SceneSwitch() {
		SceneManager.LoadScene (levelToLoad);
	}


}
