import { useEffect, useState } from "react";
import axios from "axios";

export default function Stats({stats, setStats}){

    return (
        <footer className="stats"> 
            <p>Average time to finish tasks: {stats.averageTotalTime} minutes</p>
            <br></br>
            <p>Average time to finish tasks by priority: </p>
            <ul>
                <li>Low: {stats.averageLowTime} minutes</li>
                <li>Medium: {stats.averageMediumTime} minutes</li>
                <li>High: {stats.averageHighTime} minutes</li>
            </ul>
        </footer>
    );
}