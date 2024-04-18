import { useEffect, useState } from "react"

const Star
    = () => {
        const [fullname, setFullName] = useState('');
        const [dateOfBirth, setDateOfBirth] = useState('');
        const [gender, setGender] = useState('');
        const [nationality, setNationality] = useState([]);
        const [description, setDescription] = useState('');
        const [success, setSuccess] = useState(false);
        const [error, setError] = useState(false);



        useEffect(() => {
            fetch('http://localhost:9999/')
                .then((result) => {
                    return result.json()
                })
                .then((result) => {

                })
        }, []);

        function handleSubmit() {
            if(fullname == '') {
                setError(true);
            }
            else {
                let newS = {
                    FullName: fullname,
                    Male: gender,
                    Dob: dateOfBirth,
                    Description: description,
                    Nationality: nationality
                }
    
                fetch('http://localhost:9999/product', {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify(newS)
                    })
                        .then((result) => {
                            setSuccess(true);
                        }).catch((err) => {
                            console.log(err);
                        });
            }
            

        }


        //  d-flex justify-content-center
        return (
            <div className="col-12 py-5">
                <div className="col-12 d-flex justify-content-center">
                    <h1 className="mx-5 col-7">Create a new Star</h1>
                </div>
                <div className="col-12 d-flex justify-content-center">
                    {
                        success && (
                            <div className="col-7 py-2 d-flex"
                            style={{backgroundColor: 'rgb(128, 238, 128)', color:'green'}}
                            >Created successfully</div>
                        )
                    }
                </div>

                <div className="col-12 d-flex justify-content-center">

                    <div className="col-8">
                        <div className="form-group col-12 d-flex justify-content-center">
                            <div className="col-5">
                                <label htmlFor="id">ID</label>
                                <input className="form-control col-12" type="text" id="id" readOnly value={0} />
                            </div>
                            <div className="col-5">
                                <label htmlFor="fullname">Fullname</label>
                                <input className="form-control col-12" type="text" id="fullname"
                                    onChange={(e) => {
                                        setFullName(e.target.value);
                                    }}
                                />
                                <p id="validatefullname" style={{ color: 'red' }}>{error == true ? 'FullName is required' : ''}</p>
                            </div>
                        </div>
                        <div className="form-group col-12 d-flex justify-content-center">
                            <div className="col-5">
                                <label htmlFor="dateofbirth">Date Of Birth</label>
                                <input className="form-control col-12" type="date" id="dateofbirth"
                                    onChange={(e) => { setDateOfBirth(e.target.value) }}
                                />
                            </div>
                            <div className="col-5">
                                <label htmlFor="gender">Gender</label>
                                <div className="col-5">

                                    <input className="mx-2" type="radio" name="gender" value={true} id="gender"
                                        onClick={(e) => { setGender(e.target.value) }}
                                    /> Male
                                    <input className="mx-2" type="radio" name="gender" value={false} id="gender"
                                        onClick={(e) => { setGender(e.target.value) }}
                                    /> Female
                                </div>
                            </div>
                        </div>
                        <div className="form-group col-12 d-flex justify-content-center">
                            <div className="col-5">
                                <label htmlFor="nationality">Nationality</label>
                                <select name="nationality" id="nationality" className="form-control col-12"
                                    onChange={(e) => { setNationality(e.target.value) }}
                                >
                                    <option > -- Select --</option>
                                    <option value="USA">USA</option>
                                    <option value="England">England</option>
                                    <option value="France">France</option>

                                </select>
                            </div>
                            <div className="col-5">
                                <label htmlFor="Description">Description</label>
                                <input className="form-control col-12" type="text" id="Description"
                                    onChange={(e) => {
                                        setDescription(e.target.value);
                                    }}
                                />
                            </div>
                        </div>
                        <div className="form-group col-12 d-flex justify-content-center">
                            <button className="btn btn-primary px-5 mr-3" onClick={() => handleSubmit()}>Add</button>
                            <button className="btn btn-danger">Reset</button>
                        </div>
                    </div>
                </div>
            </div>

        )
    }

export default Star
