using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;

public class ToStandardMode : MonoBehaviour {

	public string levelToLoad = "StandardScreen";



	public void SceneSwitch() {
		SceneManager.LoadScene (levelToLoad);
	}


}