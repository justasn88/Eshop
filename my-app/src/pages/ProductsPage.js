import React, {useEffect, useState} from 'react';
import axios from "axios";
import {useParams, Route, Link} from "react-router-dom";
import ImageGallery from "react-image-gallery";
import "react-image-gallery/styles/css/image-gallery.css";


function ProductsPage() {

    const {categoryId} = useParams();
    const [category, setCategory] = useState(null);
    const [products, setProducts] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);





    useEffect(() => {
        const fetchData = async () => {
            try {
                const url = categoryId
                    ? `http://localhost:8080/product?categoryId= ${categoryId}`
                    : 'http://localhost:8080/product';
                const response = await axios.get(url);
                setProducts(response.data);

                if (categoryId) {
                    const categoryResponse = await axios.get(`http://localhost:8080/category/${categoryId}`);
                    setCategory(categoryResponse.data);
                }
                else {
                    setCategory(null);
                }

            } catch (err) {
                setError(err)
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [categoryId]);


    if (loading) {
        return <div>Loading...</div>;
    }
    if (error) {
        return <div>Error: {error.message}</div>
    }

 return (
    <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
    <h1>{category ? category.name : 'All Products'}</h1>
{products.length > 0 ? (
        <ul>
            {products.map(product => (
                <li key={product.id} style={{display:'flex', marginBottom: '10px', padding: '10px', border: '1px solid black', borderRadius: '5px', backgroundColor: '#E5FCF5', alignItems: 'flex-start'}}>

                    <Link to={`/product/${product.id}`} style ={{
                        textDecoration: 'none',
                        color: 'inherit',
                        display: 'flex',
                        width: '100%'
                    }}>

                    {product.images && product.images.length > 0 ? (
                        <div style = {{marginRight: '10px'}}>
                                <img key = {product.images[0].id} src={product.images[0].path} alt = {product.name} width ="50px" height = "50px" />
                        </div>

                    ): null}
                    <div style={{display:'flex', flexDirection:'column'}}>
                        <h3 style = {{margin: '0'}}>{product.name}</h3>
                        <p style = {{margin: '0', fontSize: '10px'}}>{product.description}</p>
                    </div>
                    </Link>
                </li>
            ))}
        </ul>
    ) : (
        <p>No products found for this category.</p>
    )}
</div>
);
}

export default ProductsPage;