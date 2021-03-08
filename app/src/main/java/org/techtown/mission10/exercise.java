package org.techtown.mission10;

public class exercise {
    String name;
    int ImageId;
    String Sets;
    String Reps;
    String Weight;
    String timeGap;
    String buttonText;
    boolean Added = false;

    public boolean isAdded() {
        return Added;
    }

    public void setAdded(boolean aorT) {
        Added = aorT;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getSets() {
        return Sets;
    }

    public void setSets(String sets) {
        Sets = sets;
    }

    public String getReps() {
        return Reps;
    }

    public void setReps(String reps) {
        Reps = reps;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getTimeGap() {
        return timeGap;
    }

    public void setTimeGap(String timeGap) {
        this.timeGap = timeGap;
    }

    public exercise(String name, int imageId, String sets, String reps, String weight, String timeGap, String ButtonText, boolean aorT) {
        this.name = name;
        ImageId = imageId;
        Sets = sets;
        Reps = reps;
        Weight = weight;
        this.timeGap = timeGap;
        this.buttonText = ButtonText;
        this.Added = aorT;
    }
}
