import React from "react";

function E04_6() {
  const people = [
    { name: "Jack", age: 50 },
    { name: "Michael", age: 9 },
    { name: "John", age: 40 },
    { name: "Ann", age: 19 },
    { name: "Elisabeth", age: 16 },
  ];
  // find tìm người đầu tiên
  const firstTeenager = people.find(
    (person) => person.age >= 10 && person.age <= 20
  );
  // filter lọc full
  console.log(firstTeenager);
  const allTeenagers = people.filter(
    (person) => person.age >= 10 && person.age <= 20
  );

  console.log(allTeenagers);
  const isEveryoneTeenager = people.every(
    (person) => person.age >= 10 && person.age <= 20
  );
  const isAnyOneTeenager = people.some(
    (person) => person.age >= 10 && person.age <= 20
  );
  return (
    <div>
      <h1>Kiểm tra Thanh Thiếu Niên</h1>
      <p>
        Người thanh thiếu niên đầu tiên:{" "}
        {firstTeenager
          ? firstTeenager.name + " " + firstTeenager.age + " tuổi"
          : "Không có"}
      </p>
      <p>
        Tất cả mọi người là thanh thiếu niên:{" "}
        {allTeenagers.map((person) => person.name + person.age).join(", ")}
      </p>
      <p>
        Tất cả mọi người có phải là thanh thiếu niên không?{" "}
        {isEveryoneTeenager ? "Có" : "Không"}
      </p>
      <p>
        Có ai là thanh thiếu niên không? {isAnyOneTeenager ? "Có" : "Không"}
      </p>
    </div>
  );
}

export default E04_6;
