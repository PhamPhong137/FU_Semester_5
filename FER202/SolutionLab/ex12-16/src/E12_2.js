import React, { useState } from 'react';

const E12_2 = () => {
  const [text, setText] = useState(''); // Khởi tạo trạng thái với giá trị ban đầu là chuỗi rỗng

  // Hàm xử lý sự kiện thay đổi trên trường nhập liệu
  const handleChange = (event) => {
    setText(event.target.value); // Cập nhật trạng thái với giá trị mới nhập vào
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '20px', backgroundColor: 'grey', padding: '20px', color: 'white' }}>
      <input type="text" value={text} onChange={handleChange} style={{ marginBottom: '10px' }} />
      <p>Chữ nhập vào: {text}</p>
    </div>
  );
};

export default E12_2;
