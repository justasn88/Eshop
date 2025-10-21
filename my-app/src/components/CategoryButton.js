import React, { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import './CategoryButton.css';

function HomePage() {
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isTreeVisible, setIsTreeVisible] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState(null);
    const timerRef = useRef(null);
    const buttonRef = useRef(null);

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await axios.get('http://localhost:8080/category');
                setCategories(response.data);
            } catch (err) {
                setError(err);
            } finally {
                setLoading(false);
            }
        };
        fetchCategories();
    }, []);

    const showTreeWithDelay = () => {
        if (timerRef.current) {
            clearTimeout(timerRef.current);
        }
        timerRef.current = setTimeout(() => {
            setIsTreeVisible(true);
        }, 300);
    };

    const hideTreeWithDelay = () => {
        if (timerRef.current) {
            clearTimeout(timerRef.current);
        }
        timerRef.current = setTimeout(() => {
            setIsTreeVisible(false);
            setSelectedCategory(null);
        }, 100);
    };

    const handleCategorySelect = (category) => {
        setSelectedCategory(category);
    };

    const renderRightPanelSubcategories = (subcategories, level = 0) => {
        return subcategories.map(subCategory => (
            <div key={subCategory.id}>
                {level === 0 ? (
                    <h4 onMouseEnter={showTreeWithDelay} style={{ marginBottom: '15px' }}>
                        <Link
                            to={`/products/${subCategory.id}`}
                            style={{ textDecoration: 'none', color: '#333' }}
                        >
                            {subCategory.name}
                        </Link>
                    </h4>
                ) : (
                    <div onMouseEnter={showTreeWithDelay} style={{
                        marginBottom: '5px',
                        fontSize: '14px',
                        fontWeight: 'normal',
                        marginLeft: `${level * 10}px`
                    }}>
                        <Link
                            to={`/products/${subCategory.id}`}
                            style={{ textDecoration: 'none', color: '#555' }}
                        >
                            {subCategory.name}
                        </Link>
                    </div>
                )}
                {subCategory.children && subCategory.children.length > 0 && (
                    <div>
                        {renderRightPanelSubcategories(subCategory.children, level + 1)}
                    </div>
                )}
            </div>
        ));
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    const topLevelCategories = categories.filter(cat => !cat.parentId);

    return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
            <div
                style={{ position: 'relative', display: 'inline-block' }}
                onMouseLeave={hideTreeWithDelay}
            >
                <Link to="/products" ref={buttonRef}
                      onMouseEnter={showTreeWithDelay}
                      className='category-button'>

                    {isTreeVisible ? '▼ ' : '▶ '}
                    Kategorijos

                </Link>
                {isTreeVisible && (
                    <div
                        onMouseEnter={showTreeWithDelay}
                        style={{
                            position: 'absolute',
                            top: '100%',
                            left: '0',
                            width: selectedCategory ? '800px' : '250px',
                            backgroundColor: 'white',
                            border: '1px solid #ccc',
                            boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
                            zIndex: 1000,
                            display: 'flex',
                            flexDirection: 'row',
                            padding: '5px',
                            transition: 'width 0.3s ease-in-out'
                        }}
                    >
                        {/* Left Sidebar */}
                        <div
                            style={{ width: '250px', borderRight: '1px solid #eee', paddingRight: '10px' }}
                            onMouseEnter={showTreeWithDelay}
                        >
                            {topLevelCategories.map(category => (
                                <Link
                                    key={category.id}
                                    to={`/products/${category.id}`}
                                    onMouseEnter={() => handleCategorySelect(category)}
                                    style={{
                                        display: 'block',
                                        textDecoration: 'none',
                                        color: 'inherit',
                                        padding: '10px',
                                        cursor: 'pointer',
                                        backgroundColor: selectedCategory && selectedCategory.id === category.id ? '#f0f0f0' : 'transparent',
                                        whiteSpace: 'nowrap',
                                        overflow: 'hidden',
                                        textOverflow: 'ellipsis'
                                    }}
                                >
                                    {category.name}
                                </Link>
                            ))}
                        </div>
                        {/* Right Content Area - Conditionally rendered */}
                        {selectedCategory && (
                            <div
                                style={{ width: '550px', paddingLeft: '20px' }}
                                onMouseEnter={showTreeWithDelay}
                            >
                                {selectedCategory.children && selectedCategory.children.length > 0 ? (
                                    renderRightPanelSubcategories(selectedCategory.children)
                                ) : (
                                    <div>No subcategories available.</div>
                                )}
                            </div>
                        )}
                    </div>
                )}
            </div>
        </div>
    );
}

export default HomePage;