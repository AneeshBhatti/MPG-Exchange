import React, { useState } from "react";
import './App.css';

function App() {
    //Different states for holding different inputs and data
    const [startYear, setStartYear] = useState('');
    const [endYear, setEndYear] = useState('');
    const [model, setModel] = useState('');
    const [data, setData] = useState([]);
    const [error, setError] = useState('');

    //function to retrieve data from API
    const handleFetchData = async () => {
        //validate input was entered
        if (!startYear && !endYear && !model) {
            setError("You must enter at least a year or a model.");
            return;
        }

        //construct url
        let url = `http://localhost:8080/api/fueleconomy/toyota?startYear=${startYear || ''}&endYear=${endYear || ""}`;
        //if model was inputted, add model to url
        if (model) {
            url += `&model=${model}`;
        }

        //try to recieve data
        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Failed to fetch data');
            }
            const result = await response.json();   //pass the JSON data
            setData(result);    //store data
            setError('');   //clear errors
        } catch (err) {
            setError(err.message);
        }
    };

    //determine css class based on MPG value
    const getMpgClass = (mpg) => {
        if (mpg === "Error fetching MPG data") {
            return "error";
        }
        if (mpg === "MPG data not available") {
            return "not-available"; //class for unavailable mpg data
        }
        return "success";   //class for successful
    };

    return (
        <div className="App">
            <div className="App-header">
                <h1>MPG Exchange</h1>

                {/* Logo Added Below the Header */}
                <div className="logo-container">
                    <img src="/toyota.png" alt="MPG Exchange Logo" className="logo" />
                </div>
            </div>

            <label>
                Start Year:
                <input
                    type="number"
                    value={startYear}
                    onChange={e => setStartYear(e.target.value)}    //update state when input changes
                />
            </label>
            <br />

            <label>
                End Year:
                <input
                    type="number"
                    value={endYear}
                    onChange={e => setEndYear(e.target.value)}  //update state when input changes
                />
            </label>
            <br />

            <label>
                Model:
                <input
                    type="text"
                    value={model}
                    onChange={e => setModel(e.target.value)}    //update state when input changes
                />
            </label>
            <br />

            {/*button to submit and retrieve data*/}

            <button onClick={handleFetchData}>Get Data</button>

            {error && <p style={{color: 'red'}}>{error}</p>}

            {/*display results in table*/}
            <div>
                <h2>Results:</h2>
                <table>
                    <thead>
                    <tr>
                        <th>Year</th>
                        <th>Model</th>
                        <th>MPG</th>
                    </tr>
                    </thead>
                    <tbody>
                    {data.map((result, index) => (
                        <tr key={index}>
                            <td>{result.year}</td>
                            <td>{result.modelName}</td>
                            <td className={getMpgClass(result.mpg)}>{result.mpg}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default App;
