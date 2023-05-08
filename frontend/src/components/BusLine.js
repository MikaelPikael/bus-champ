import React from "react";
import { useState, useEffect } from "react";
import {
  Alert,
  Spinner,
  List,
  ListItem,
} from "@material-tailwind/react";

const BusLine = ({ lineId, open }) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/lines/${lineId}/stops`);
        if (!response.ok) {
          throw new Error(
            `This is an HTTP error: The status is ${response.status}`
          );
        }
        let actualData = await response.json();
        console.log(actualData);
        setData(actualData);
        setError(null);
      } catch (err) {
        setError(err.message);
        setData(null);
      } finally {
        setLoading(false);
      }
    };
    
    if (open && data === null) {
        getData();
    }
    
  }, [open]);

  return (
    <div>
      {loading && <Spinner className="h-12 w-12" />}
      {error && (
        <Alert color="red" variant="gradient">
          <span>Failed to load bus lines! - {error}</span>
        </Alert>
      )}
      <List>
        {data !== null && data.map((stop, index) => 
         <ListItem>{index + 1}: {stop.name} ({stop.number})</ListItem>
        )}
      </List>
    </div>
  );
};

export default BusLine;
