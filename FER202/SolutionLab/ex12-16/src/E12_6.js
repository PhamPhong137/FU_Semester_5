import React, { useState } from 'react';

const E12_6 = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [items] = useState(['Apple', 'Banana', 'Cherry', 'Date', 'Grape', 'Lemon', 'Mango', 'Orange', 'Peach', 'Watermelon']);

  // Hàm xử lý sự kiện thay đổi trên trường nhập liệu
  const handleChange = (event) => {
    setSearchTerm(event.target.value);
  };

  // Lọc các mục dựa trên thông tin tìm kiếm
  const filteredItems = items.filter(item => 
    item.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      <input
        type="text"
        placeholder="Search items..."
        value={searchTerm}
        onChange={handleChange}
      />
      <ul>
        {filteredItems.map((item, index) => (
          <li key={index}>{item}</li>
        ))}
      </ul>
    </div>
  );
};

export default E12_6;
