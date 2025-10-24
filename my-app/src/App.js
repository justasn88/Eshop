// src/App.js

import React from 'react';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import ProductsPage from './pages/ProductsPage';
import ProductDetailsPage from './pages/ProductDetailsPage';
import CategoryButton from './components/CategoryButton';
import HomePageButton from './components/HomePageButton';
import CartButton from './components/CartButton';
import Cart from "./pages/CartPage";

function App() {
    return (
        <div>
            <nav style={{
                padding: '10px 20px',
                backgroundColor: '#f4f4f4',
                borderBottom: '1px solid #ddd',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
            }}>
                <div style={{ display: 'flex', gap: '10px' }}>
                    <div style={{ height: '50px', display: 'flex', alignItems: 'center' }}>
                        <HomePageButton />
                    </div>
                    <div style={{ height: '50px', display: 'flex', alignItems: 'center' }}>
                        <CategoryButton />
                    </div>
                </div>
                <div style={{ height: '50px', display: 'flex', alignItems: 'center' }}>
                    <CartButton />
                </div>
            </nav>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/products" element={<ProductsPage />} />
                <Route path="/products/:categoryId" element={<ProductsPage />} />
                <Route path="/product/:productId" element={<ProductDetailsPage />} />
                <Route path="/cart" element={<Cart />}></Route>
            </Routes>
        </div>
    );
}

export default App;