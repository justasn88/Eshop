import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import ImageGallery from "react-image-gallery";
import "react-image-gallery/styles/css/image-gallery.css";
import AddToCartButton from "../components/AddToCartButton";
import CategoryButton from "../components/CategoryButton";

function ProductDetailsPage() {
    const { productId } = useParams();
    const [product, setProduct] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/product/${productId}`);
                setProduct(response.data);
            } catch (err) {
                setError(err);
            } finally {
                setLoading(false);
            }
        };

        fetchProduct();
    }, [productId]);

    if (loading) {
        return <div>Loading product...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    if (!product) {
        return <div>Product not found.</div>;
    }

    const images = product.images.map(image => ({
        original: image.path,
        thumbnail: image.path,
        originalHeight: 300,
        originalWidth: 300,

    }));

    return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif', marginLeft: '50px' }}>
            <h1>{product.name}</h1>
            <div style={{ display: 'flex', gap: '20px' }}>
                <div style={{ width: '30%' }}>
                    {images.length > 0 ? (
                        <ImageGallery items={images} showPlayButton={false} showFullscreenButton={true} />
                    ) : (
                        <div>No images available.</div>
                    )}
                </div>
                <div style={{ width: '30%' }}>
                    <h3>Aprašymas</h3>
                    <p>{product.description}</p>
                    <p>{product.price}</p>
                    <div style={{ height: '50px', display: 'flex', alignItems: 'center' }}>
                        <AddToCartButton/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ProductDetailsPage;