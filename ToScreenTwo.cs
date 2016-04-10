using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;

public class ToScreenTwo : MonoBehaviour {

	public string levelToLoad = "Screen2";



	public void SceneSwitch() {
		SceneManager.LoadScene (levelToLoad);
	}


	}
