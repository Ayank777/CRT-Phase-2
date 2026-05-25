import React, {
  useEffect,
  useState
} from "react";

function App() {

  const [patients, setPatients] =
    useState([]);

  const [patientName,
    setPatientName] =
    useState("");

  const [daysAdmitted,
    setDaysAdmitted] =
    useState("");

  const [search,
    setSearch] =
    useState("");

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

    if (!patientName || !daysAdmitted) {

      alert("Please fill all fields");

      return;
    }

    const patient = {

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

        body:
          JSON.stringify(patient)
      });

      loadPatients();

      setPatientName("");
      setDaysAdmitted("");

    } catch (error) {

      console.error(error);
    }
  };

  // Delete Patient
  const deletePatient = async (id) => {

    if (!window.confirm(
      "Delete this patient?"
    )) return;

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

  // Search Filter
  const filteredPatients =
    patients.filter((p) =>
      p.patientName
        .toLowerCase()
        .includes(
          search.toLowerCase()
        )
    );

  // Statistics
  const totalPatients =
    patients.length;

  const totalRevenue =
    patients.reduce(
      (sum, p) =>
        sum + p.totalFee,
      0
    );

  return (

    <div className=
      "min-h-screen bg-slate-100 p-6"
    >

      <div className=
        "max-w-7xl mx-auto"
      >

        {/* Header */}
        <div className=
          "bg-blue-700 text-white p-6 rounded-2xl shadow-lg mb-6"
        >

          <h1 className=
            "text-4xl font-bold"
          >
            Hospital Management Dashboard
          </h1>

          <p className=
            "mt-2 text-blue-100"
          >
            Full Stack Cloud Application
          </p>

        </div>

        {/* Stats */}
        <div className=
          "grid grid-cols-1 md:grid-cols-2 gap-6 mb-6"
        >

          <div className=
            "bg-white rounded-2xl shadow-md p-6"
          >

            <h2 className=
              "text-gray-500 text-lg"
            >
              Total Patients
            </h2>

            <p className=
              "text-4xl font-bold text-blue-700 mt-2"
            >
              {totalPatients}
            </p>

          </div>

          <div className=
            "bg-white rounded-2xl shadow-md p-6"
          >

            <h2 className=
              "text-gray-500 text-lg"
            >
              Total Revenue
            </h2>

            <p className=
              "text-4xl font-bold text-green-600 mt-2"
            >
              ₹ {totalRevenue}
            </p>

          </div>

        </div>

        {/* Form */}
        <div className=
          "bg-white rounded-2xl shadow-md p-6 mb-6"
        >

          <h2 className=
            "text-2xl font-semibold mb-4"
          >
            Admit Patient
          </h2>

          <div className=
            "grid grid-cols-1 md:grid-cols-4 gap-4"
          >

            <input
              type="text"
              placeholder="Patient Name"
              value={patientName}
              onChange={(e) =>
                setPatientName(
                  e.target.value
                )
              }
              className=
                "border p-3 rounded-xl"
            />

            <input
              type="number"
              placeholder="Days Admitted"
              value={daysAdmitted}
              onChange={(e) =>
                setDaysAdmitted(
                  e.target.value
                )
              }
              className=
                "border p-3 rounded-xl"
            />

            <button
              onClick={addPatient}
              className=
                "bg-blue-700 hover:bg-blue-800 text-white rounded-xl px-4 py-3 font-semibold"
            >
              Admit Patient
            </button>

            <input
              type="text"
              placeholder="Search Patient"
              value={search}
              onChange={(e) =>
                setSearch(
                  e.target.value
                )
              }
              className=
                "border p-3 rounded-xl"
            />

          </div>

        </div>

        {/* Table */}
        <div className=
          "bg-white rounded-2xl shadow-md overflow-hidden"
        >

          <table className=
            "w-full"
          >

            <thead className=
              "bg-blue-700 text-white"
            >

              <tr>

                <th className=
                  "p-4 text-left"
                >
                  ID
                </th>

                <th className=
                  "p-4 text-left"
                >
                  Name
                </th>

                <th className=
                  "p-4 text-left"
                >
                  Days
                </th>

                <th className=
                  "p-4 text-left"
                >
                  Fee
                </th>

                <th className=
                  "p-4 text-left"
                >
                  Action
                </th>

              </tr>

            </thead>

            <tbody>

              {filteredPatients.map((p) => (

                <tr
                  key={p.patientId}
                  className=
                    "border-b hover:bg-slate-50"
                >

                  <td className=
                    "p-4"
                  >
                    {p.patientId}
                  </td>

                  <td className=
                    "p-4"
                  >
                    {p.patientName}
                  </td>

                  <td className=
                    "p-4"
                  >
                    {p.daysAdmitted}
                  </td>

                  <td className=
                    "p-4"
                  >
                    ₹ {p.totalFee}
                  </td>

                  <td className=
                    "p-4"
                  >

                    <button
                      onClick={() =>
                        deletePatient(
                          p.patientId
                        )
                      }
                      className=
                        "bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg"
                    >
                      Delete
                    </button>

                  </td>

                </tr>

              ))}

            </tbody>

          </table>

        </div>

      </div>

    </div>
  );
}

export default App;