Khởi tạo Project ReactJS:          create-react-app  + tên file
Cài React-Bootstrap                 npm install react-bootstrap bootstrap
Import thư viện react-bootstrap     import 'bootstrap/dist/css/bootstrap.min.css';
Cài Json-server:		   	npm install json-server@0.17.4 -g 
Watch json-server                   json-server --watch db.json -p 3000 
<!-- PE -->
					json-server --watch database.json -p 9999

Cài Axios:                          npm install --save axios
Router            		    npm i react-router-dom
Redux                               npm i redux@4.2.1
					npm i react-redux@8.1.3
					npm i redux-thunk@2.4.2 	
rfec tab -> 


npm list -g --depth=0
npm uninstall json-server -g

npm install json-server@0.17.4 -g (cài đúng phiên bản)(tránh phiên bản apha)

Set-ExecutionPolicy RemoteSigned: cách fix npx 
<!-- fetch data lan dau -->
useEffect(() => {
    fetchProducts();   
  }, []);
<!-- get -->
  const fetchProducts = async () => {
    const response = await axios.get("http://localhost:3000/products");
    setProducts(response.data);
  };
  <!-- them -->
  const newOrder = {
      id: orderId,
      customerId: selectedCustomer,
      productId: selectedProduct,
      quantity: Number(quantity)
    };
    await axios.post('http://localhost:3001/orders', newOrder);
  <!-- Xoa -->
    const deleteOrder = async (id) => {
    await axios.delete(`http://localhost:3000/orders/${id}`);
  };
<!-- Update -->
  const updateOrder = async () => {
    const id = selectedOrder.id;
    await axios.put(`http://localhost:3000/orders/${id}`, {
      customerId: selectedOrder.customerId,
      productId: selectedProductId,
      quantity: quantity,
    });
  };
  <!-- Sua it truong -->
  await axios.patch(`http://localhost:9999/todo/${id}`, {
      completed: changeStatus,
    });
<!-- get byid -->
const getDirecterNameById = (id) => {
    return directors?.find((d) => d.id === id)?.FullName;
  };

<!-- Param -->
<Link to={`/dishes/${dish.id}`}>
              <Button variant="primary">View Details</Button>
            </Link>
			const { dishId } = useParams();
  const dish = dishes.find(d => d.id === parseInt(dishId, 10));

  <Route path="/dishes/:dishId" element={<DishDetail />} />

  <!-- id tang -->
   const generateNewId = () => {
    const ids = users.map((user) => user.id);
    const maxId = Math.max(...ids);
    return maxId + 1;
  };
  <!-- luu vao mang -->
  const fetchCustomers = async () => {
        const response = await axios.get('http://localhost:3001/customers');
        const customersMap = response.data.reduce((acc, customer) => {
            acc[customer.id] = { name: customer.name, email: customer.email };
            return acc;
        }, {});
        setCustomers(customersMap); //customers
    };
