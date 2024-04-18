import React, { createContext, useContext, useReducer } from 'react';

// Tạo một Cart Context
const CartContext = createContext();

// Data của các món ăn
const sampleProducts = [
    {
        "id": 0,
        "name": "Uthappizza",
        "image": "images/uthappizza.png",
        "category": "mains",
        "label": "Hot",
        "price": "4.99",
        "featured": true,
        "description": "A unique combination of Indian Uthappam (pancake) and Italian pizza, topped with Cerignola olives, ripe vine cherry tomatoes, Vidalia onion, Guntur chillies and Buffalo Paneer."
    },
    {
        "id": 1,
        "name": "Zucchipakoda",
        "image": "images/zucchipakoda.png",
        "category": "appetizer",
        "label": "",
        "price": "1.99",
        "featured": false,
        "description": "Deep fried Zucchini coated with mildly spiced Chickpea flour batter accompanied with a sweet-tangy tamarind sauce"
    },
    {
        "id": 2,
        "name": "Vadonut",
        "image": "images/vadonut.png",
        "category": "appetizer",
        "label": "New",
        "price": "1.99",
        "featured": false,
        "description": "A quintessential ConFusion experience, is it a vada or is it a donut?"
    },
    {
        "id": 3,
        "name": "ElaiCheese Cake",
        "image": "images/elaicheesecake.png",
        "category": "dessert",
        "label": "",
        "price": "2.99",
        "featured": false,
        "description": "A delectable, semi-sweet New York Style Cheese Cake, with Graham cracker crust and spiced with Indian cardamoms"
    }]
    ;

// Một reducer để xử lý các hành động trong giỏ hàng
function cartReducer(state, action) {
    switch (action.type) {
        case 'THEM_VAO_GIO':
            // Tìm xem sản phẩm đã có trong giỏ hàng chưa
            const existingIndex = state.items.findIndex(item => item.id === action.payload.id);
            // Nếu sản phẩm đã có, tăng số lượng
            if (existingIndex > -1) {
                let items = [...state.items];
                items[existingIndex] = {
                    ...items[existingIndex],
                    quantity: items[existingIndex].quantity + 1
                };
                return {
                    ...state,
                    items: items
                };
            } else {
                // Nếu chưa có, thêm sản phẩm vào giỏ hàng
                return {
                    ...state,
                    items: [...state.items, { ...action.payload, quantity: 1 }],
                };
            }
        case 'XOA_KHOI_GIO':
            return {
                ...state,
                items: state.items.filter(item => item.id !== action.payload)
            };
        case 'DAT_LAI_GIO':
            return {
                ...state,
                items: []
            };
        default:
            throw new Error('Loại hành động không được hỗ trợ.');
    }
}

// Component cung cấp Context cho các component con
export const CartProvider = ({ children }) => {
    const initialState = {
        items: [], // Danh sách các sản phẩm trong giỏ hàng
    };

    const [cartState, dispatch] = useReducer(cartReducer, initialState);

    return (
        <CartContext.Provider value={{ cartState, dispatch }}>
            {children}
        </CartContext.Provider>
    );
};

// Component Cart sử dụng CartContext để hiển thị thông tin giỏ hàng
export const Cart = () => {
    const { cartState, dispatch } = useContext(CartContext);

    // Tính tổng giá trị của giỏ hàng
    const totalValue = cartState.items.reduce((total, item) => total + item.price * item.quantity, 0);

    return (
        <div>
            <h2>Giỏ hàng của bạn</h2>
            <p>Tổng số mục: {cartState.items.length}</p>
            <p>Tổng giá trị: ${totalValue.toFixed(2)}</p>
            {cartState.items.map((item) => (
                <div key={item.id}>
                    <p>{item.name}</p>
                    <p>Số lượng: {item.quantity}</p>
                    <p>Giá: ${item.price}</p>
                    <button onClick={() => dispatch({ type: 'XOA_KHOI_GIO', payload: item.id })}>
                        Xóa
                    </button>
                </div>
            ))}
            <button onClick={() => dispatch({ type: 'DAT_LAI_GIO' })}>
                Xóa tất cả
            </button>
        </div>
    );
};

// Component hiển thị danh sách các món ăn và cho phép thêm vào giỏ hàng
const ProductsList = () => {
    const { dispatch } = useContext(CartContext);

    const handleAddToCart = product => {
        dispatch({ type: 'THEM_VAO_GIO', payload: product });
    };

    return (
        <div>
            <h2>Sản phẩm</h2>
            {sampleProducts.map(product => (
                <div key={product.id}>
                    <p>{product.name}</p>
                    <p>Giá: ${product.price}</p>
                    <button onClick={() => handleAddToCart(product)}>
                        Thêm vào giỏ hàng
                    </button>
                </div>
            ))}
        </div>
    );
};

// Component hiển thị giỏ hàng và các món ăn đã chọn


// Ứng dụng chính
const E14_2 = () => {
    return (
        <CartProvider>
            <div>
                <h1>Cửa hàng trực tuyến</h1>
                <ProductsList />
                <Cart />
            </div>
        </CartProvider>
    );
};

export default E14_2;
