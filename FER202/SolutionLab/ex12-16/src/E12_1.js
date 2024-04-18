import React, { useState } from "react";

const E12_1 = () => {
  // State to hold the count value
  const [count, setCount] = useState(0);
  console.log(count);
  // Function to increment the count
  const handleIncrement = () => {
    setCount((prevCount) => prevCount + 1);
  };

  return (
    <div
      style={{
        textAlign: "center",
        marginTop: "20px",
        backgroundColor: "grey",
        padding: "20px",
        color: "white",
      }}
    >
      <button onClick={handleIncrement}>Increment</button>
      <h2>Count: {count}</h2>
    </div>
  );
};

export default E12_1;
