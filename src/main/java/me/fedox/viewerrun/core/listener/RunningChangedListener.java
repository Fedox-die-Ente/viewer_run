package me.fedox.viewerrun.core.listener;

/**
 * Interface for listening to changes in the running state of the VRModel.
 */
public interface RunningChangedListener extends ModelListener {
    /**
     * Called when the running state changes.
     *
     * @param running the new running state
     */
    void runningChanged(boolean running);
}