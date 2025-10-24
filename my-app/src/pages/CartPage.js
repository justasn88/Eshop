import React, { useState, useEffect } from 'react';

// API bazinis adresas
const API_BASE_URL = 'http://localhost:8080/api/cart';

function Cart() {
    // Būsenos (State) valdymas
    const [cart, setCart] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    // FUNKCIJA: Krepšelio duomenų įkėlimas
    const fetchCart = async () => {
        setIsLoading(true);
        try {
            const response = await fetch(API_BASE_URL, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include', // Būtina sesijos ID perdavimui
            });

            if (!response.ok) {
                throw new Error('Klaida gaunant krepšelio duomenis. Statusas: ' + response.status);
            }

            const data = await response.json();
            setCart(data);
            setError(null);
        } catch (err) {
            setError(err.message);
            // Jei fetch nepavyksta, nustatome tuščią sąrašą
            setCart({ cartItems: [], totalPrice: 0, totalQuantity: 0 });
        } finally {
            setIsLoading(false);
        }
    };

    // FUNKCIJA: Prekės kiekio atnaujinimas
    const updateQuantity = async (itemId, newQuantity) => {
        const newIntQuantity = parseInt(newQuantity);

        if (isNaN(newIntQuantity) || newIntQuantity < 0) return;

        if (newIntQuantity === 0) {
            removeItem(itemId);
            return;
        }

        try {
            const response = await fetch(API_BASE_URL + '/update', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                // ⚠️ Užtikriname, kad itemId ir newQuantity būtų perduoti
                body: JSON.stringify({ itemId: itemId, newQuantity: newIntQuantity }),
            });

            if (!response.ok) {
                throw new Error(`API klaida: ${response.statusText}`);
            }

            fetchCart(); // Atnaujiname krepšelį
        } catch (err) {
            alert(`Klaida atnaujinant kiekį! ${err.message}`);
            fetchCart(); // Atstatome seną kiekį
        }
    };

    // FUNKCIJA: Prekės pašalinimas
    const removeItem = async (itemId) => {
        // ⚠️ Patikrinimas: Užtikriname, kad itemId yra validus ir vartotojas patvirtina
        if (!itemId) {
            console.error("Negalima pašalinti: itemId yra null/undefined.");
            alert("Klaida: Prekės identifikatorius trūksta.");
            return;
        }

        if (!window.confirm('Ar tikrai norite pašalinti šią prekę?')) return;

        try {
            const response = await fetch(API_BASE_URL + '/remove', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                // ⚠️ Užtikriname, kad itemId yra perduodamas
                body: JSON.stringify({ itemId: itemId }),
            });

            if (!response.ok) {
                throw new Error(`API klaida: ${response.statusText}`);
            }

            fetchCart(); // Atnaujiname krepšelį
        } catch (err) {
            alert(`Klaida šalinant prekę! ${err.message}`);
            fetchCart();
        }
    };

    // Įkelti duomenis, kai komponentas užkraunamas
    useEffect(() => {
        fetchCart();
    }, []);

    // ========== RENDER PAGRINDINĖ LOGIKA ==========

    if (isLoading) return <div style={{ textAlign: 'center', padding: '20px' }}>Įkeliama...</div>;
    if (error) return <div style={{ color: 'red', textAlign: 'center', padding: '20px' }}>Klaida: {error}.</div>;

    // Saugiai gauname elementų sąrašą (Naudojame 'cartItems' pavadinimą!)
    const itemsList = cart?.cartItems || [];

    if (itemsList.length === 0) {
        return <div style={{ textAlign: 'center', padding: '20px' }}>Jūsų krepšelis yra tuščias.</div>;
    }

    return (
        <div style={{ padding: '20px' }}>
            <h2>Jūsų Krepšelis</h2>
            <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '15px' }}>
                <thead>
                <tr style={{ borderBottom: '2px solid #ddd' }}>
                    <th style={{ padding: '10px', textAlign: 'left' }}>Prekė</th>
                    <th style={{ padding: '10px', textAlign: 'right' }}>Kaina</th>
                    <th style={{ padding: '10px', textAlign: 'center' }}>Kiekis</th>
                    <th style={{ padding: '10px', textAlign: 'right' }}>Viso</th>
                    <th style={{ padding: '10px' }}></th>
                </tr>
                </thead>
                <tbody>
                {itemsList.map(item => (
                    // ⚠️ Naudojame item.itemId kaip 'key'
                    <tr key={item.itemId}>
                        <td style={{ padding: '10px', display: 'flex', alignItems: 'center' }}>
                            {item.imageUrl && (
                                <img src={item.imageUrl} alt={item.productName} style={{ width: '50px', height: '50px', marginRight: '10px', objectFit: 'cover' }} />
                            )}
                            {item.productName}
                        </td>
                        <td style={{ padding: '10px', textAlign: 'right' }}>{item.price ? item.price.toFixed(2) : '0.00'} €</td>
                        <td style={{ padding: '10px', textAlign: 'center' }}>
                            <input
                                type="number"
                                value={item.quantity}
                                min="0"
                                style={{ width: '60px', textAlign: 'center' }}
                                // ⚠️ Kvietimas su item.itemId
                                onChange={(e) => updateQuantity(item.itemId, e.target.value)}
                            />
                        </td>
                        <td style={{ padding: '10px', textAlign: 'right' }}>{item.totalPrice ? item.totalPrice.toFixed(2) : '0.00'} €</td>
                        <td style={{ padding: '10px', textAlign: 'center' }}>
                            {/* ⚠️ Kvietimas su item.itemId */}
                            <button onClick={() => removeItem(item.itemId)} style={{ background: 'red', color: 'white', border: 'none', padding: '5px 10px', cursor: 'pointer' }}>Pašalinti</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <h3 style={{ textAlign: 'right', marginTop: '20px', borderTop: '2px solid #333', padding: '10px 0' }}>
                Bendra Suma: {cart.totalPrice ? cart.totalPrice.toFixed(2) : '0.00'} €
            </h3>
        </div>
    );
}

export default Cart;