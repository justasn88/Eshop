import React, { useState } from 'react';
import './AddToCartButton.css';

function AddToCartButton({ productId }) {

    const [quantity, setQuantity] = useState(1);

    const handleAddToCart = async () => {
        if (!productId) {
            alert('Klaida: Nepateiktas produkto ID.');
            return;
        }

        const dataToSend = {
            productId: productId,
            quantity: quantity
        };

        try {
            const response = await fetch('/api/cart/items', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dataToSend)
            });

            if (response.ok) {
                alert(`Prekė '${productId}' (${quantity} vnt.) pridėta į krepšelį!`);
            } else if (response.status === 400) {
                const errorData = await response.json();
                console.error('Validacijos klaida:', errorData);
                alert('Nepavyko pridėti: ' + JSON.stringify(errorData));
            } else {
                alert('Nepavyko pridėti prekės. Serverio klaida.');
                console.error('Serverio atsakas ne OK:', response.status);
            }
        } catch (error) {
            console.error('Tinklo klaida siunčiant užklausą:', error);
            alert('Ryšio klaida. Patikrinkite serverį.');
        }
    };

    return (
        <div>
            <input
                type="number"
                min="1"
                value={quantity}
                onChange={(e) => setQuantity(parseInt(e.target.value) || 1)}
                style={{ width: '50px', marginRight: '10px' }}
            />

            <button
                className='add-to-cart-button'
                onClick={handleAddToCart}
            >
                Pridėti į krepšelį
            </button>
        </div>
    );
}

export default AddToCartButton;