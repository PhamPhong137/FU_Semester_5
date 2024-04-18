import AddProduct from "./AddProduct";
import FilterProductByCategory from "./FilterProductByCategory";
import ProductList from "./ProductList";
import SearchProduct from "./SearchProduct";
function App() {
  return (
    <div>
      <ProductList />
      <SearchProduct />
      <FilterProductByCategory />
      <AddProduct />
    </div>
  );
}

export default App;
