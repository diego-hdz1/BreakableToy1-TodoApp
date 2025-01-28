import { useEffect, useState } from "react";
import React, {memo} from "react";

interface StatsProps{
    stats:{
        averageTotalTime: number;
        averageLowTime: number;
        averageMediumTime: number;
        averageHighTime: number;
        numberPages: number;
    };

}

function convertMinutes(minutes: number): string{
    const days = Math.floor(minutes/(24*60));
    const hours = Math.floor((minutes % (24*60))/60);
    const finalMinutes = minutes % 60;

    return `${days} days, ${hours} hours and ${finalMinutes} minutes`;

}

const Stats = memo(function({stats}: StatsProps){
//    export default function Stats({stats}: StatsProps){
    console.log("Cuantas veces entra");
    return (
        <footer className="stats"> 
            <h3>Average time to finish tasks:</h3>
            <p> {convertMinutes(stats.averageTotalTime)} </p>
            <br></br>
            <h3>Average time to finish tasks by priority: </h3>
            <ul>
                <li> <span style={{color:"green", fontSize:"1.5em"}}>Low:</span> {convertMinutes(stats.averageLowTime)}</li>
                <li> <span style={{color:"orange", fontSize:"1.5em"}}>Medium:</span> {convertMinutes(stats.averageMediumTime)} </li>
                <li> <span style={{color:"red", fontSize:"1.5em"}}>High:</span> {convertMinutes(stats.averageHighTime)}</li>
            </ul>
        </footer>
    );
});

export default Stats;