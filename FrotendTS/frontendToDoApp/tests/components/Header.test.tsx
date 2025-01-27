import { it, expect, describe } from 'vitest';
import {render, screen} from '@testing-library/react';
import Header from '../../src/components/Header';
import * as React from 'react';
import '@testing-library/jest-dom/vitest';

describe('Header', () => {
    it('should render To Dos Application 📗', () => {
        render(<Header/>);
        const heading = screen.getByRole('heading');
        expect(heading).toBeInTheDocument();
        expect(heading).toHaveTextContent(/To Dos Application/i);
    });
})