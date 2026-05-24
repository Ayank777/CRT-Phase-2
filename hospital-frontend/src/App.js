import React, { useEffect, useState } from "react";

function App() {

  const [patients, setPatients] = useState([]);

  const [patientId, setPatientId] = useState("");

  const [patientName, setPatientName] = useState("");

  const [daysAdmitted, setDaysAdmitted] = useState("");

  const API_URL =
    "https://max-hospital.onrender.com/patients";

  // Load Patients
  const loadPatients = async () => {

    try {

      const response =
        await fetch(API_URL);

      const data =
        await response.json();

      setPatients(data);

    } catch (error) {

      console.error(error);
    }
  };

  // Add Patient
  const addPatient = async () => {

    const patient = {

      patientId: parseInt(patientId),

      patientName,

      daysAdmitted:
        parseInt(daysAdmitted),

      totalFee:
        parseInt(daysAdmitted) * 700
    };

    try {

      await fetch(API_URL, {

        method: "POST",

        headers: {
          "Content-Type":
            "application/json"
        },

        body: JSON.stringify(patient)
      });

      loadPatients();

      setPatientId("");
      setPatientName("");
      setDaysAdmitted("");

    } catch (error) {

      console.error(error);
    }
  };

  // Delete Patient
  const deletePatient = async (id) => {

    try {

      await fetch(
        `${API_URL}/${id}`,
        {
          method: "DELETE"
        });

      loadPatients();

    } catch (error) {

      console.error(error);
    }
  };

  useEffect(() => {

    loadPatients();

  }, []);

  return (

    <div
      style={{
        padding: "30px",
        fontFamily: "Arial",
        background:
          "#E3F2FD",
        minHeight: "100vh"
      }}
    >

      <h1>
        Hospital Management System
      </h1>

      <div
        style={{
          marginBottom: "20px"
        }}
      >

        <input
          type="number"
          placeholder="Patient ID"
          value={patientId}
          onChange={(e) =>
            setPatientId(
              e.target.value
            )}
        />

        <input
          type="text"
          placeholder="Patient Name"
          value={patientName}
          onChange={(e) =>
            setPatientName(
              e.target.value
            )}
          style={{
            marginLeft: "10px"
          }}
        />

        <input
          type="number"
          placeholder="Days"
          value={daysAdmitted}
          onChange={(e) =>
            setDaysAdmitted(
              e.target.value
            )}
          style={{
            marginLeft: "10px"
          }}
        />

        <button
          onClick={addPatient}
          style={{
            marginLeft: "10px"
          }}
        >
          Admit
        </button>

      </div>

      <table
        border="1"
        cellPadding="10"
        style={{
          background: "white",
          width: "100%"
        }}
      >

        <thead>

          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Days</th>
            <th>Fee</th>
            <th>Action</th>
          </tr>

        </thead>

        <tbody>

          {patients.map((p) => (

            <tr key={p.patientId}>

              <td>{p.patientId}</td>

              <td>{p.patientName}</td>

              <td>{p.daysAdmitted}</td>

              <td>{p.totalFee}</td>

              <td>

                <button
                  onClick={() =>
                    deletePatient(
                      p.patientId
                    )}
                >
                  Delete
                </button>

              </td>

            </tr>

          ))}

        </tbody>

      </table>

    </div>
  );
}

export default App;