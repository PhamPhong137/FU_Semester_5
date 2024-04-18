import logo from "./logo.svg";
import "./App.css";
const products = [
  { name: "Sản phẩm 1", category: "Điện tử", price: 1200, year: 2019 },
  { name: "Sản phẩm 2", category: "Thời trang", price: 300, year: 2021 },
  { name: "Sản phẩm 3", category: "Đồ gia dụng", price: 150, year: 2020 },
  { name: "Sản phẩm 4", category: "Thực phẩm", price: 50, year: 2022 },
  { name: "Sản phẩm 5", category: "Điện tử", price: 800, year: 2018 },
  { name: "Sản phẩm 6", category: "Thể thao", price: 250, year: 2020 },
  { name: "Sản phẩm 7", category: "Giáo dục", price: 100, year: 2019 },
  { name: "Sản phẩm 8", category: "Văn phòng phẩm", price: 20, year: 2021 },
  { name: "Sản phẩm 9", category: "Thời trang", price: 500, year: 2019 },
  { name: "Sản phẩm 10", category: "Sức khỏe", price: 600, year: 2022 },
  { name: "Sản phẩm 11", category: "Du lịch", price: 700, year: 2018 },
  { name: "Sản phẩm 12", category: "Công nghệ", price: 1100, year: 2020 },
  { name: "Sản phẩm 13", category: "Thời trang", price: 400, year: 2022 },
  { name: "Sản phẩm 14", category: "Điện tử", price: 850, year: 2021 },
  { name: "Sản phẩm 15", category: "Nội thất", price: 250, year: 2019 },
  { name: "Sản phẩm 16", category: "Thể thao", price: 550, year: 2018 },
  { name: "Sản phẩm 17", category: "Đồ chơi", price: 60, year: 2020 },
  { name: "Sản phẩm 18", category: "Sức khỏe", price: 320, year: 2021 },
  { name: "Sản phẩm 19", category: "Giáo dục", price: 180, year: 2019 },
  { name: "Sản phẩm 20", category: "Văn phòng phẩm", price: 90, year: 2022 },
];

function c1(products) {
  return products.map((product) => product.name);
}
function c2(products, category_name) {
  return products.filter((product) => product.category === category_name);
}
function c3(products) {
  return products.sort((a, b) => a.price - b.price);
}

function c4(products) {
  return (
    <div>
      <table border={1}>
        <tr>
          <td>Name</td>
          <td>Price</td>
        </tr>
        {products.map((product) => (
          <tr>
            <td>{product.name}</td>
            <td>{product.price}</td>
          </tr>
        ))}
      </table>
    </div>
  );
}
function c5(products) {
  return products.reverse();
}
function c6(products) {
  let sum = products.reduce((a, b) => a + b.price, 0);
  return sum / products.length;
}
function c7(products) {
  let sortedProduct = products.sort((a, b) => a.price - b.price);
  return ` Highest: ${sortedProduct[products.length - 1].price}
            Lowest :${sortedProduct[0].price}
    `;
}
function c8(products, max, min) {
  return products.filter(
    (product) => product.price >= min && product.price <= max
  );
}
function c9() {
  const year = products.map((product) => product.year);

  const uniqueYears = [...new Set(year)];
  const groupByYear = {};

  for (let year of uniqueYears) {
    groupByYear[year] = products.filter(product => product.year === year);
  }
}
function c10(products) {
  return (
    <ul>
      {products.map((product) => (
        <li>
          {product.name} <button>Details</button>
        </li>
      ))}
    </ul>
  );
}
function c11(products, nameProduct) {
  products.filter(product => product.name === nameProduct);
}
function c12(products, category_name) {
  let sum = products.filter((product) => product.category === category_name);
  return sum / products.length;
}
function c13(products) {
  let sortedProductbyYear = products.sort((a, b) => a.year - b.year);
  return ` Newest: ${sortedProductbyYear[products.length - 1].year}
            Oldest :${sortedProductbyYear[0].year}
      `;
}
function c14(products,criterion) {
    products.filter(criterion)
}
function c15(products, category_name) {
  let total = products.filter((product) => product.category === category_name);
  if (total.length === 0) {
    return `There are no products in a category`;
  }
  return total;
}
function c16(products) {
  const a = [];
  let sortedProduct = products.sort((a, b) => a.price - b.price);
  a.push(sortedProduct[products.length - 1]);
  a.push(sortedProduct[0]);
  return a;
}
function c17(products) {
  const colors = ["yellow", "blue", "green", "red", "orange"];
  return (
    <div>
      {products.map((product) => (
        <div
          style={{
            color: ` ${colors[Math.floor(Math.random() * colors.length)]}`,
          }}
        >
          {product.name}
        </div>
      ))}
    </div>
  );
}
function c18(products) {
  return (
    <select>
      {products.map((product) => (
        <option> {product.category}</option>
      ))}
    </select>
  );
}
function c19(products) {
  return (
    <div>
      {products.map((product) => (
        <div
          onClick={() => {
            alert(
              `Name: ${product.name} || Category: ${product.category}|| Price: ${product.price} || Year: ${product.year}`
            );
          }}
        >
          {product.name}{" "}
        </div>
      ))}
    </div>
  );
}
function c20(products) {
  const productCategory = {};
  products.map((product) => {
    if (productCategory[product.category]) {
      productCategory[product.category]++;
    } else {
      productCategory[product.category] = 1;
    }
  });
  return productCategory;
}

function App() {
  return <div className="App"></div>;
}

export default App;
