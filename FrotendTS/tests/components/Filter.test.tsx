import { it, expect, describe } from 'vitest';
import {fireEvent, render, screen} from '@testing-library/react';
import Filter from '../../src/components/Filter';
import * as React from 'react';
import '@testing-library/jest-dom/vitest';
import {vi} from 'vitest';
import {MemoryRouter, BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";

const mockHandleNameFilter = vi.fn();
const mockHandleFilterPriority = vi.fn();
const mockhandleFilterDone = vi.fn();
const mockSetData = vi.fn();

const defaultProps ={
    nameFilter: 'All',
    filterPriority: 0,
    filterDone: 'Done',
    pagination: 1,
    handleNameFilter: mockHandleNameFilter,
    handleFilterPriority: mockHandleFilterPriority,
    handleFilterDone: mockhandleFilterDone,
    setData: mockSetData,
    ordenation: 0,
    dateSort: 0,
}

describe('Filter', () => {
    it('should render correctly the text', () => {
        render(
            <MemoryRouter>
                <Filter {...defaultProps}/>
            </MemoryRouter>
        );

        expect(screen.getByText("Choose your options to filter")).toBeInTheDocument();
        expect(screen.getByText("Filter")).toBeInTheDocument();
        expect(screen.getByText("Add To Do")).toBeInTheDocument();
    })

    it('should', () => {
        
    })
})