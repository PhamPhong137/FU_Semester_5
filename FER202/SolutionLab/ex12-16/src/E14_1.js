import React, { useState, useContext, createContext } from 'react';

// 1. Định nghĩa các theme
const themes = {
  light: {
    foreground: "#000000",
    background: "#eeeeee"
  },
  dark: {
    foreground: "#ffffff",
    background: "#61dafb"
  }
};

// 2. Tạo một Theme Context
const ThemeContext = createContext();

// 3. Tạo một Theme Provider Component
const ThemeProvider = ({ children }) => {
  const [themeName, setThemeName] = useState('light'); // Bắt đầu với theme sáng

  const toggleTheme = () => {
    setThemeName(themeName === 'light' ? 'dark' : 'light');
  };

  const theme = themes[themeName];

  return (
    <ThemeContext.Provider value={{ theme, toggleTheme }}>
      {children}
    </ThemeContext.Provider>
  );
};

// 4. Tạo một Theme Button Component
const ThemeButton = () => {
  const { theme, toggleTheme } = useContext(ThemeContext);

  return (
    <button
      onClick={toggleTheme}
      style={{ background: theme.background, color: theme.foreground }}
    >
      Toggle Theme
    </button>
  );
};

// 5. App Component
const E14_1 = () => {
  return (
    <ThemeProvider>
      <div>
        <ThemeButton />
      </div>
    </ThemeProvider>
  );
};

export default E14_1;
