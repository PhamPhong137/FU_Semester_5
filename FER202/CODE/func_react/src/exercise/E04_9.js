import React from 'react'

function E04_9() {
  class Shape {
      constructor(color) {
          this.color = color;
      }

      getArea() {
          throw new Error('Phương thức getArea() phải được triển khai bởi lớp con');
      }

      toString() {
          return `Shape with color: ${this.color}`;
      }
  }

  // Lớp con Rectangle
  class Rectangle extends Shape {
      constructor(color, length, width) {
          super(color);
          this.length = length;
          this.width = width;
      }

      getArea() {
          return this.length * this.width;
      }

      toString() {
          return `Rectangle with color: ${this.color}, length: ${this.length}, width: ${this.width}`;
      }
  }

  // Lớp con Triangle
  class Triangle extends Shape {
      constructor(color, base, height) {
          super(color);
          this.base = base;
          this.height = height;
      }

      getArea() {
          return (this.base * this.height) / 2;
      }

      toString() {
          return `Triangle with color: ${this.color}, base: ${this.base}, height: ${this.height}`;
      }
  }

  // Sử dụng các lớp
  const rect = new Rectangle('red', 5, 3);
  console.log(rect.toString());
  console.log('Diện tích hình chữ nhật:', rect.getArea());

  const tri = new Triangle('blue', 4, 7);
  console.log(tri.toString());
  console.log('Diện tích tam giác:', tri.getArea());
  return (
      <div>E04_9</div>
  )
}

export default E04_9
