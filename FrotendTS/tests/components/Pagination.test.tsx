import { it, expect, describe } from 'vitest';
import {fireEvent, render, screen} from '@testing-library/react';
import Pagination from '../../src/components/Pagination';
import * as React from 'react';
import '@testing-library/jest-dom/vitest';
import {vi} from 'vitest';
import {MemoryRouter, BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";

const mockHandlePagination = vi.fn();
const mockHandleData = vi.fn();

const defaultProps ={
    nameFilter: '',
  filterPriority: 0,
  filterDone: '',
  pagination: 1,
  handlePagination: mockHandlePagination,
  handleData: mockHandleData,
  ordenation: 0,
  dateSort: 0,
  stats:{
    numberPages: 5,
  }
}

// vi.mock('react-router-dom', ()=>({
//     ...vi.importActual('react-router-dom') as object,
//     useNavigate: vi.fn(()=>vi.fn()),
// }));

describe('Pagination test controll', () => {
    it('should call handle pagination when pagination previous button is clicked', () => {
            render(
                <MemoryRouter>
                    <Pagination {...defaultProps}/>
                </MemoryRouter>
            );
        fireEvent.click(screen.getByText('←'));
        expect(mockHandlePagination).toHaveBeenCalledWith(0);
    });

    it('doesnt increment pagination  when its out of bounds', () => {
        render(
            <MemoryRouter>
                <Pagination {...defaultProps} pagination={5} stats={{numberPages:5}}/>
            </MemoryRouter>
        );
        fireEvent.click(screen.getAllByText('→')[0]);
        expect(mockHandlePagination).not.toHaveBeenCalledWith(6);
    });

    it('doesnt decrement pagination when its out of bounds', () => {
        render(
            <MemoryRouter>
                <Pagination {...defaultProps} pagination={0}/>
            </MemoryRouter>
        );
        fireEvent.click(screen.getAllByText('←')[0]);
        expect(mockHandlePagination).not.toHaveBeenCalledWith(-1);
    });

    it('renders initial pagination correctly', () => {
        render(
            <MemoryRouter>
                <Pagination {...defaultProps}/>
            </MemoryRouter>
        );
        expect(screen.getAllByText('Page 2')[0]).toBeInTheDocument();
    });

    it('calls next pagination button correctly', () => {
        render(
            <MemoryRouter>
                <Pagination {...defaultProps}/>
            </MemoryRouter>
        );
        fireEvent.click(screen.getAllByText('→')[0]);
        expect(mockHandlePagination).toHaveBeenCalledWith(2);
    });

})