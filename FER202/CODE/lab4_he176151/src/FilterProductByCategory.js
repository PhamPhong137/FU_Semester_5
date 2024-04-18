import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Table, Dropdown, DropdownButton } from "react-bootstrap";
import axios from "axios";

function FilterProductByCategory() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("All Categories");

  useEffect(() => {
    fetchProducts();
    fetchCategories();
  }, [products]);

  const fetchProducts = async () => {
    const response = await axios.get("http://localhost:3000/products");
    setProducts(response.data);
  };

  const fetchCategories = async () => {
    const response = await axios.get("http://localhost:3000/categories");
    setCategories(response.data);
  };

  const handleSelectCategory = (category) => {
    setSelectedCategory(category);
  };

  const filteredProducts = selectedCategory === "All Categories"
  ? products
  : products.filter(product => {
    const categoryId = categories.find(categorie => categorie.name === selectedCategory).id; 
    return product.categoryId.toString() === categoryId;
  });

  return (
    <>
      <DropdownButton
        id="dropdown-basic-button"
        title={selectedCategory}
        onSelect={handleSelectCategory}
      >
        <Dropdown.Item eventKey="All Categories">All Categories</Dropdown.Item>
        {categories.map((category) => (
          <Dropdown.Item key={category.id} eventKey={category.name}>
            {category.name}
          </Dropdown.Item>
        ))}
      </DropdownButton>

      <Table striped bordered hover>
        <tbody>
          {filteredProducts.map((product) => {
            return (
              <tr key={product.id}>
                <td>{product.id}</td>
                <td>{product.name}</td>
                <td>{product.categoryId}</td>
                <td>{product.price}</td>
                <td>{product.description}</td>
              </tr>
            );
          })}
        </tbody>
      </Table>
    </>
  );
}

export default FilterProductByCategory;
