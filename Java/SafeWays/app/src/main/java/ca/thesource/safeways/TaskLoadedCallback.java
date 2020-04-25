package ca.thesource.safeways;

/**
 * Referenced to android-maps-direction repo by Vishal Shrestha
 *  @author Vishal Shrestha
 * Created on 10/20/2018.
 * REPO LINK : https://github.com/Vysh01/android-maps-directions
 * Used for parsing JSON request from GMaps API to be plotted
 */

public interface TaskLoadedCallback {
    void onTaskDone(Object... values);
}
