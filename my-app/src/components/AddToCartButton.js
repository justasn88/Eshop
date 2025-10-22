import React, { useState } from 'react';
import './AddToCartButton.css';

// Komponentas dabar priima produkto ID kaip prop'są.
function AddToCartButton({ productId }) {
    // 1. Sukuriame būseną (State) kiekiui valdyti, tarkime, numatytasis kiekis yra 1.
    // Jei turite atskirą įvesties lauką, galite naudoti prop'są arba useRef.
    const [quantity, setQuantity] = useState(1);

    // 2. Funkcija užklausos siuntimui
    const handleAddToCart = async () => {
        if (!productId) {
            alert('Klaida: Nepateiktas produkto ID.');
            return;
        }

        const dataToSend = {
            productId: productId,
            quantity: quantity // Naudojame būsenoje esantį kiekį
        };

        try {
            const response = await fetch('/api/cart/items', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                // Būtina, kad Spring validacija veiktų
                body: JSON.stringify(dataToSend)
            });

            if (response.ok) {
                // Sėkmės atveju
                alert(`Prekė '${productId}' (${quantity} vnt.) pridėta į krepšelį!`);
                // Čia galite atnaujinti globalią krepšelio būseną
            } else if (response.status === 400) {
                // Validacijos klaidos
                const errorData = await response.json();
                console.error('Validacijos klaida:', errorData);
                alert('Nepavyko pridėti: ' + JSON.stringify(errorData));
            } else {
                // Kitos klaidos (pvz., 500 Serverio klaida)
                alert('Nepavyko pridėti prekės. Serverio klaida.');
                console.error('Serverio atsakas ne OK:', response.status);
            }
        } catch (error) {
            // Tinklo klaidos
            console.error('Tinklo klaida siunčiant užklausą:', error);
            alert('Ryšio klaida. Patikrinkite serverį.');
        }
    };

    return (
        <div>
            {/* Pridėkite kiekio valdymo lauką, jei to dar neturite */}
            <input
                type="number"
                min="1"
                value={quantity}
                onChange={(e) => setQuantity(parseInt(e.target.value) || 1)}
                style={{ width: '50px', marginRight: '10px' }}
            />

            {/* Mygtuko paspaudimas kviečia užklausos funkciją */}
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