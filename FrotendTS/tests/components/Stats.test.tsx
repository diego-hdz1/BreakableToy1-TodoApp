import { it, expect, describe } from 'vitest';
import {render, screen} from '@testing-library/react';
import Stats from '../../src/components/Stats';
import * as React from 'react';
import '@testing-library/jest-dom/vitest';
import { waitFor } from "@testing-library/react";

describe('Stats', () => {
    const stats = {averageTotalTime: 45, averageLowTime: 30,averageMediumTime: 40,averageHighTime: 59,numberPages: 10};

    it("Renders average total time", ()=>{
        render(<Stats stats={stats}/>);
        expect(screen.getByText(/Average time to finish tasks:/i)).toBeInTheDocument();
        expect(screen.getByText(/45 minutes/i)).toBeInTheDocument();
    });

    it("Renders structure", ()=>{
        render(<Stats stats={stats}/>);
        expect(screen.getAllByRole("listitem")).toHaveLength(6);
    });

    it("Renders average High time", ()=>{
        render(<Stats stats={stats}/>);
        expect(screen.getAllByText(/59 minutes/i)[0]).toBeInTheDocument();
    });


})