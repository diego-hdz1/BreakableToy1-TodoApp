import { describe, it, expect, beforeEach } from 'vitest';
import {fireEvent, render, screen} from '@testing-library/react';
import ToDoData from '../../src/components/ToDoData';
import * as React from 'react';
import '@testing-library/jest-dom/vitest';
import {vi} from 'vitest';
import { MemoryRouter, useNavigate, useParams, Route, Routes } from 'react-router-dom';
// import * as router from "react-router-dom";

 

// vi.mock("react-router-dom",  () =>({
//     const actual = vi.importActual("react-router-dom") as object,
//     return {

//         //     ...actual,
//         //    useNavigate: () => mockNavigte,
//         //    useParams: () => ({id:undefined})
//     }
    
// }))

const mockFetchStats = vi.fn();
const mockNavigte = vi.fn();



describe('ToDoData', () => {

    beforeEach(()=>{
        vi.clearAllMocks();
    });

    it('Renders Add To do title when id is no present', () => {
        render(
            <MemoryRouter>
                <ToDoData fetchStats={mockFetchStats}/>
            </MemoryRouter>
        );
        expect(screen.getByText(/Add To Do/i)).toBeInTheDocument();
    })

    it('updates input fields', () => {
        render(
            <MemoryRouter>
                <ToDoData fetchStats={mockFetchStats}/>
            </MemoryRouter>
        )

        const nameInput = screen.getAllByPlaceholderText("Enter to do name")[0];
        fireEvent.change(nameInput, {target: {value:"New Task"}});
        expect(nameInput).toHaveValue("New Task");
    })

    // it('goes back to home when you click in the cancel button', () => {
    //     render(
    //         <MemoryRouter>
    //             <ToDoData fetchStats={mockFetchStats}/>
    //         </MemoryRouter>
    //     );
    //     const cancelButton = screen.getAllByText(/Cancel/i)[0];
    //     fireEvent.click(cancelButton);
    //     expect(cancelButton).toBeInTheDocument();
    //     //expect(mockNavigte).toHaveBeenCalledTimes(1);
    //     //expect(mockNavigte).toHaveBeenCalledWith("/");
    // })
})
