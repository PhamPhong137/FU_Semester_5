import React from 'react'


function E04_7() {
    var array = [1, 2, 3, 4];

    const sum = array.reduce((accumulator, currentValue) => accumulator + currentValue,0);
    const product = array.reduce((accumulator, currentValue) => accumulator * currentValue, 1);
    return (
        <div>
            <h1>Kết quả từ Mảng</h1>
            <p>Tổng của mảng: {sum}</p>
            <p>Tích của mảng: {product}</p>
        </div>
    )
}

export default E04_7
