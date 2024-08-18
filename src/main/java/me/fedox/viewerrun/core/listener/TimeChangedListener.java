package me.fedox.viewerrun.core.listener;

/**
 * Interface for listening to time changes in the VRModel.
 */
public interface TimeChangedListener extends ModelListener {
    /**
     * Called when the left time changes.
     *
     * @param leftTime the new left time
     */
    void onTimeChanged(long leftTime);
}