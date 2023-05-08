import React from "react";
import { useState, useEffect } from "react";
import {
  Alert,
  Typography,
  Spinner,
  Accordion,
  AccordionHeader,
  AccordionBody,
} from "@material-tailwind/react";
import BusLine from "./BusLine";

const BusLines = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [open, setOpen] = useState(0);
  const handleOpen = (value) => {
    setOpen(open === value ? 0 : value);
  };

  const getStops = 

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/lines`);
        if (!response.ok) {
          throw new Error(
            `This is an HTTP error: The status is ${response.status}`
          );
        }
        let actualData = await response.json();
        setData(actualData);
        setError(null);
      } catch (err) {
        setError(err.message);
        setData(null);
      } finally {
        setLoading(false);
      }
    };
    getData();
  }, []);

  return (
    <div className="w-full lg:p-8 p-4 flex items-center justify-between">
      <div className="lg:w-[60%] w-full lg:px-6 lg:pl-10">
        <Typography className="text-3xl ...(truncated)">
          Top 10 Bus lines
        </Typography>
        <Typography className="font-poppins mb-6">
          A list of bus lines in Stockholm with the most stops.
        </Typography>
        {loading && <Spinner className="h-12 w-12" />}
        {error && (
          <Alert color="red" variant="gradient">
            <span>Failed to load bus lines! - {error}</span>
          </Alert>
        )}
        {data !== null && data.map((line, index) => (
          <Accordion key={index} open={open === index + 1}>
            <AccordionHeader onClick={() => handleOpen(index + 1)}>
              Line {line.name} - {line.numberOfStops} stops
            </AccordionHeader>
            <AccordionBody>
              <BusLine key={index} lineId={line.name} open={open == index + 1}></BusLine>
            </AccordionBody>
          </Accordion> 
        ))}
      </div>
    </div>
  );
};

export default BusLines;
