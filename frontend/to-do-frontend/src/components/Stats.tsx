import { useEffect, useState } from "react";
import React from "react";

interface StatsProps{
    stats:{
        averageTotalTime: number;
        averageLowTime: number;
        averageMediumTime: number;
        averageHighTime: number;
        numberPages: number;
    };

    setStats: (stats:{
        averageTotalTime: number;
        averageLowTime: number;
        averageMediumTime: number;
        averageHighTime: number;
        numberPages: number;
    }) => void;
}

export default function Stats({stats, setStats}: StatsProps){
    return (
        <footer className="stats"> 
            <p>Average time to finish tasks: {stats.averageTotalTime.toString()} minutes</p>
            <br></br>
            <p>Average time to finish tasks by priority: </p>
            <ul>
                <li>Low: {stats.averageLowTime.toString()} minutes</li>
                <li>Medium: {stats.averageMediumTime.toString()} minutes</li>
                <li>High: {stats.averageHighTime.toString()} minutes</li>
            </ul>
        </footer>
    );
}