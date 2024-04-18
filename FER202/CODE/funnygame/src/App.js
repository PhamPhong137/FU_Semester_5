import { useState } from "react";

function App() {
  const [isHovered, setIsHovered] = useState(false);

  const randomBox = () => {
    return {
      top: `${Math.floor(Math.random() * 100)}px`,
      left: `${Math.floor(Math.random() * 100)}px`,
    };
  };
  return (
    <div
      style={{
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div
        style={{
          width: "400px",
          height: "400px",
          border: "2px solid black",
          position: "relative",
        }}
      >
        <div
          style={{
            position: "absolute",
            width: "10px",
            height: "10px",
            border: "2px solid black",
            ...randomBox(),
          }}
          onMouseEnter={() => setIsHovered(true)}
          onMouseLeave={() => setIsHovered(false)}
        ></div>
      </div>
    </div>
  );
}

export default App;
