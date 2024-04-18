import React, { useState } from 'react';

const E12_7 = () => {
  const [items, setItems] = useState(['Item 1', 'Item 2', 'Item 3', 'Item 4', 'Item 5']);
  const [draggingIndex, setDraggingIndex] = useState(null);

  const handleDragStart = (index) => {
    setDraggingIndex(index);
  };

  const handleDragEnter = (index) => {
    if (index !== draggingIndex) {
      const newList = [...items];
      const itemDragged = newList.splice(draggingIndex, 1)[0];
      newList.splice(index, 0, itemDragged);
      setItems(newList);
      setDraggingIndex(index);
    }
  };

  const handleDragEnd = () => {
    setDraggingIndex(null);
  };

  return (
    <ul style={{ listStyleType: 'circle' }}>
      {items.map((item, index) => (
        <li
          key={index}
          draggable
          onDragStart={() => handleDragStart(index)}
          onDragEnter={() => handleDragEnter(index)}
          onDragEnd={handleDragEnd}
          style={{ cursor: 'move', padding: '5px' }}
        >
          {item}
        </li>
      ))}
    </ul>
  );
};

export default E12_7;
