import React from 'react';
import { Link } from 'react-router-dom';
import './CartButton.css';

function CartButton() {
    return (
        <Link to="/cart" className='cart-button'>
                Krepšelis
        </Link>
    )
}

export default CartButton;