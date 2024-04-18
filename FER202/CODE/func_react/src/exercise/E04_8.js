import React from "react";

function E04_8() {
  const companies = [
    { name: "Company One", category: "Finance", start: 1981, end: 2004 },
    { name: "Company Two", category: "Retail", start: 1992, end: 2008 },
    { name: "Company Three", category: "Auto", start: 1999, end: 2007 },
    { name: "Company Four", category: "Retail", start: 1989, end: 2010 },
    { name: "Company Five", category: "Technology", start: 2009, end: 2014 },
    { name: "Company Six", category: "Finance", start: 1987, end: 2010 },
    { name: "Company Seven", category: "Auto", start: 1986, end: 1996 },
    { name: "Company Eight", category: "Technology", start: 2011, end: 2016 },
    { name: "Company Nine", category: "Retail", start: 1981, end: 1989 },
  ];

  const ages = [33, 12, 20, 16, 5, 54, 21, 44, 61, 13, 15, 45, 25, 64, 32];

  const person = {
    name: "Costas",
    address: {
      street: "Lalaland 12",
    },
  };

  // Sử dụng forEach để in tên mỗi công ty
  companies.forEach((company) => console.log(company.name));

  // In tên mỗi công ty khởi nghiệp sau năm 1987
  companies.forEach((company) => {
    if (company.start > 1987) console.log(company.name);
  });

  // Lấy chỉ các công ty thuộc danh mục Retail
  // Tăng giá trị start lên 1 và chèn vào DOM
  const retailCompanies = companies
    .filter((company) => company.category === "Retail")
    .map((company) => ({ ...company, start: company.start + 1 }));

  // Sắp xếp các công ty theo ngày kết thúc tăng dần
  const sortedCompanies = companies.slice().sort((a, b) => a.end - b.end);

  // Sắp xếp mảng ages theo thứ tự giảm dần
  const sortedAges = ages.slice().sort((a, b) => b - a);

  // Tính tổng các tuổi sử dụng reduce
  const sumOfAges = ages.reduce((total, age) => total + age, 0);

  // Tạo đối tượng mới với tên và danh mục giống company đầu tiên
  const newObject = (({ name, category }) => ({
    name,
    category,
    print() {
      console.log(this.name);
    },
  }))(companies[0]);

  // Hàm tính tổng số không xác định các số
  const sum = (...numbers) =>
    numbers.reduce((total, number) => total + number, 0);

  // Hàm nhận số không xác định các đối số và trả về mảng
  const collectArgs = (...args) => [].concat(...args);

  const {
    address: { street }
  } = person;

  // Hàm trả về số tăng dần từ 0 mỗi khi gọi
  const createCounter = () => {
    let count = 0;
    return () => console.log(++count);
  };

  let c = createCounter()
  c() // counter trả về số 1
  c() // counter trả về số 2
  c() // counter trả về số 3
  
  // Hàm phân tích cú pháp các tham số truy vấn từ URL
  const parseQueryParams = (url) => {
    const queryParams = {};
    new URL(url).searchParams.forEach((value, key) => {
      queryParams[key] = value;
    });
    return queryParams;
  };
  return (
    <div>
      {/* Hiển thị các công ty Retail đã được chèn vào DOM */}
      {retailCompanies.map((company, index) => (
        <div
          key={index}
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            marginBottom: "10px",
            border: "1px solid black",
            padding: "10px",
          }}
        >
          <p>Name: {company.name}</p>
          <p>Category: {company.category}</p>
          <p>Start: {company.start}</p>
          <p>End: {company.end}</p>
        </div>
      ))}
    </div>
  );
}

export default E04_8;
