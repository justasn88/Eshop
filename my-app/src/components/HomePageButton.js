// HomePageButton.js
import React from 'react';
import { Link } from 'react-router-dom';
import './HomePageButton.css';

function HomePageButton() {
    return (
        <Link to='/' className='homepage-button'>
            Pradžia
        </Link>
    );
}

export default HomePageButton;