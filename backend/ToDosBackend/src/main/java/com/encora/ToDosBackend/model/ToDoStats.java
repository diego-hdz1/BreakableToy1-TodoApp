package com.encora.ToDosBackend.model;

public class ToDoStats {

    private Long averageTotalTime;
    private Long averageLowTime;
    private Long averageMediumTime;
    private Long averageHighTime;
    private int numberPages;

    @Override
    public String toString() {
        return "ToDoStats{" +
                "averageTotalTime=" + averageTotalTime +
                ", averageLowTime=" + averageLowTime +
                ", averageMediumTime=" + averageMediumTime +
                ", averageHighTime=" + averageHighTime +
                '}';
    }

    public ToDoStats(Long averageTotalTime, Long averageLowTime, Long averageMediumTime, Long averageHighTime, int numberPages) {
        this.averageTotalTime = averageTotalTime;
        this.averageLowTime = averageLowTime;
        this.averageMediumTime = averageMediumTime;
        this.averageHighTime = averageHighTime;
        this.numberPages = numberPages;
    }

    public int getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(int numberPages) {
        this.numberPages = numberPages;
    }

    public Long getAverageTotalTime() {
        return averageTotalTime;
    }

    public void setAverageTotalTime(Long averageTotalTime) {
        this.averageTotalTime = averageTotalTime;
    }

    public Long getAverageLowTime() {
        return averageLowTime;
    }

    public void setAverageLowTime(Long averageLowTime) {
        this.averageLowTime = averageLowTime;
    }

    public Long getAverageMediumTime() {
        return averageMediumTime;
    }

    public void setAverageMediumTime(Long averageMediumTime) {
        this.averageMediumTime = averageMediumTime;
    }

    public Long getAverageHighTime() {
        return averageHighTime;
    }

    public void setAverageHighTime(Long averageHighTime) {
        this.averageHighTime = averageHighTime;
    }
}
