import React, { useState } from "react";
import './App.css';

function App() {
    const [startYear, setStartYear] = useState('');
    const [endYear, setEndYear] = useState('');
    const [model, setModel] = useState('');
    const [data, setData] = useState([]);
    const [error, setError] = useState('');

    const handleFetchData = async () => {
        if (!startYear && !endYear && !model) {
            setError("You must enter at least a year or a model.");
            return;
        }

        let url = `http://localhost:8080/api/fueleconomy/toyota?startYear=${startYear || ''}&endYear=${endYear || ""}`;
        if (model) {
            url += `&model=${model}`;
        }

        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Failed to fetch data');
            }
            const result = await response.json();
            setData(result);
            setError('');
        } catch (err) {
            setError(err.message);
        }
    };

    const getMpgClass = (mpg) => {
        if (mpg === "Error fetching MPG data") {
            return "error";
        }
        if (mpg === "MPG data not available") {
            return "not-available";
        }
        return "success";
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
                    onChange={e => setStartYear(e.target.value)}
                />
            </label>
            <br />

            <label>
                End Year:
                <input
                    type="number"
                    value={endYear}
                    onChange={e => setEndYear(e.target.value)}
                />
            </label>
            <br />

            <label>
                Model:
                <input
                    type="text"
                    value={model}
                    onChange={e => setModel(e.target.value)}
                />
            </label>
            <br />

            <button onClick={handleFetchData}>Get Data</button>

            {error && <p style={{color: 'red'}}>{error}</p>}

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
