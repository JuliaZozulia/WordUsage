/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.juliazozulia.wordusage.Utils;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.stat.MathUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * Maintains a frequency distribution.
 * <p>
 * Accepts int, long, char or Comparable values.  New values added must be
 * comparable to those that have been added, otherwise the add method will
 * throw an IllegalArgumentException.</p>
 * <p>
 * Integer values (int, long, Integer, Long) are not distinguished by type --
 * i.e. <code>addValue(Long.valueOf(2)), addValue(2), addValue(2l)</code> all have
 * the same effect (similarly for arguments to <code>getCount,</code> etc.).</p>
 * <p>NOTE: byte and short values will be implicitly converted to int values
 * by the compiler, thus there are no explicit overloaded methods for these
 * primitive types.</p>
 * <p>
 * char values are converted by <code>addValue</code> to Character instances.
 * As such, these values are not comparable to integral values, so attempts
 * to combine integral types with chars in a frequency distribution will fail.
 * </p>
 * <p>
 * Float is not coerced to Double.
 * Since they are not Comparable with each other the user must do any necessary coercion.
 * Float.NaN and Double.NaN are not treated specially; they may occur in input and will
 * occur in output if appropriate.
 * </b>
 * <p>
 * The values are ordered using the default (natural order), unless a
 * <code>Comparator</code> is supplied in the constructor.</p>
 */
public class Frequency implements Serializable {

    /**
     * Serializable version identifier
     */
    private static final long serialVersionUID = -3845586908418844111L;
    private int totalCount;

    /**
     * underlying collection
     */
    private final HashMap<String, Integer> freqTable;

    /**
     * Default constructor.
     */
    public Frequency() {
        freqTable = new HashMap<>();
    }


    public String getKey(int position) {

        return freqTable.keySet().toArray(new String[getUniqueCount()])[position];
    }

    public String[] getItems() {

        String[] str = freqTable.keySet().toArray(new String[getUniqueCount()]);
        Arrays.sort(str, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return getCount(o2).compareTo(getCount(o1));
            }
        });
        return str;
    }

    public int getTotalCount() {

        return totalCount;

    }

    /**
     * Adds 1 to the frequency count for v.
     * <p>
     * If other objects have already been added to this Frequency, v must
     * be comparable to those that have already been added.
     * </p>
     *
     * @param v the value to add.
     * @throws MathIllegalArgumentException if <code>v</code> is not comparable with previous entries
     */
    public void addValue(String v) throws MathIllegalArgumentException {
        totalCount++;
        incrementValue(v, 1);
    }


    /**
     * Increments the frequency count for v.
     * <p>
     * If other objects have already been added to this Frequency, v must
     * be comparable to those that have already been added.
     * </p>
     *
     * @param v         the value to add.
     * @param increment the amount by which the value should be incremented
     * @throws MathIllegalArgumentException if <code>v</code> is not comparable with previous entries
     * @since 3.1
     */
    public void incrementValue(String v, Integer increment) throws MathIllegalArgumentException {

        String obj = v;

        try {
            Integer count = freqTable.get(obj);
            if (count == null) {
                freqTable.put(obj, Integer.valueOf(increment));
            } else {
                freqTable.put(obj, Integer.valueOf(count + increment));
            }
        } catch (ClassCastException ex) {
            //TreeMap will throw ClassCastException if v is not comparable
            throw new MathIllegalArgumentException(
                    LocalizedFormats.INSTANCES_NOT_COMPARABLE_TO_EXISTING_VALUES,
                    v.getClass().getName());
        }
    }


    /**
     * Clears the frequency table
     */
    public void clear() {
        freqTable.clear();
    }

    /**
     * Returns an Iterator over the set of values that have been added.
     * <p>
     * If added values are integral (i.e., integers, longs, Integers, or Longs),
     * they are converted to Longs when they are added, so the objects returned
     * by the Iterator will in this case be Longs.</p>
     *
     * @return values Iterator
     */
    public Iterator<String> valuesIterator() {
        return freqTable.keySet().iterator();
    }

    /**
     * Return an Iterator over the set of keys and values that have been added.
     * Using the entry set to iterate is more efficient in the case where you
     * need to access respective counts as well as values, since it doesn't
     * require a "get" for every key...the value is provided in the Map.Entry.
     * <p>
     * If added values are integral (i.e., integers, longs, Integers, or Longs),
     * they are converted to Longs when they are added, so the values of the
     * map entries returned by the Iterator will in this case be Longs.</p>
     *
     * @return entry set Iterator
     * @since 3.1
     */
    public Iterator<Entry<String, Integer>> entrySetIterator() {
        return freqTable.entrySet().iterator();
    }

    //-------------------------------------------------------------------------

    /**
     * Returns the sum of all frequencies.
     *
     * @return the total frequency count.
     */
    public long getSumFreq() {
        long result = 0;
        Iterator<Integer> iterator = freqTable.values().iterator();
        while (iterator.hasNext()) {
            result += iterator.next();
        }
        return result;
    }

    /**
     * Returns the number of values equal to v.
     * Returns 0 if the value is not comparable.
     *
     * @param v the value to lookup.
     * @return the frequency of v.
     */
    public Integer getCount(Comparable<?> v) {

        if (v instanceof Integer) {
            return getCount(v);
        }
        Integer result = 0;
        try {
            Integer count = freqTable.get(v);
            if (count != null) {
                result = count;
            }
        } catch (ClassCastException ex) { // NOPMD
            // ignore and return 0 -- ClassCastException will be thrown if value is not comparable
        }
        return result;
    }


    /**
     * Returns the number of values in the frequency table.
     *
     * @return the number of unique values that have been added to the frequency table.
     * @see #valuesIterator()
     */
    public int getUniqueCount() {
        return freqTable.keySet().size();
    }

    /**
     * Returns the percentage of values that are equal to v
     * (as a proportion between 0 and 1).
     * <p>
     * Returns <code>Double.NaN</code> if no values have been added.
     * Returns 0 if at least one value has been added, but v is not comparable
     * to the values set.</p>
     *
     * @param v the value to lookup
     * @return the proportion of values equal to v
     */
    public double getPct(String v) {
        final long sumFreq = getSumFreq();
        if (sumFreq == 0) {
            return Double.NaN;
        }
        return (double) getCount(v) / (double) sumFreq;
    }


    //-----------------------------------------------------------------------------------------


    public List<Comparable<?>> getMode() {
        long mostPopular = 0; // frequencies are always positive

        // Get the max count first, so we avoid having to recreate the List each time
        for (Integer l : freqTable.values()) {
            long frequency = l.longValue();
            if (frequency > mostPopular) {
                mostPopular = frequency;
            }
        }

        List<Comparable<?>> modeList = new ArrayList<Comparable<?>>();
        for (Entry<String, Integer> ent : freqTable.entrySet()) {
            Integer frequency = ent.getValue();
            if (frequency == mostPopular) {
                modeList.add(ent.getKey());
            }
        }
        return modeList;
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Returns the cumulative frequency of values less than or equal to v.
     * <p>
     * Returns 0 if v is not comparable to the values set.</p>
     *
     * @param v the value to lookup
     * @return the proportion of values equal to v
     */
    public int getCumFreq(String v) {
        if (getSumFreq() == 0) {
            return 0;
        }


        int result = 0;


   /*     if (v.compareTo(freqTable.firstKey()) < 0) {
            return 0;  // v is comparable, but less than first value
        }

        if (v.compareTo(freqTable.lastKey()) >= 0) {
            return getSumFreq();    // v is comparable, but greater than the last value
        }*/

        Iterator<String> values = valuesIterator();
        while (values.hasNext()) {
            String nextValue = values.next();
            if (v.compareTo(nextValue) > 0) {
                result += getCount(nextValue);
            } else {
                return result;
            }
        }
        return result;
    }

//----------------------------------------------------------------------------------------------

    /**
     * Merge another Frequency object's counts into this instance.
     * This Frequency's counts will be incremented (or set when not already set)
     * by the counts represented by other.
     *
     * @param other the other {@link Frequency} object to be merged
     * @throws NullArgumentException if {@code other} is null
     * @since 3.1
     */
    public void merge(final Frequency other) throws NullArgumentException {
        MathUtils.checkNotNull(other, LocalizedFormats.NULL_NOT_ALLOWED);

        final Iterator<Entry<String, Integer>> iter = other.entrySetIterator();
        while (iter.hasNext()) {
            final Entry<String, Integer> entry = iter.next();
            incrementValue(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Merge a {@link Collection} of {@link Frequency} objects into this instance.
     * This Frequency's counts will be incremented (or set when not already set)
     * by the counts represented by each of the others.
     *
     * @param others the other {@link Frequency} objects to be merged
     * @throws NullArgumentException if the collection is null
     * @since 3.1
     */
    public void merge(final Collection<Frequency> others) throws NullArgumentException {
        MathUtils.checkNotNull(others, LocalizedFormats.NULL_NOT_ALLOWED);

        for (final Frequency freq : others) {
            merge(freq);
        }
    }

    //----------------------------------------------------------------------------------------------

    /**
     * A Comparator that compares comparable objects using the
     * natural order.  Copied from Commons Collections ComparableComparator.
     */
    private static class NaturalComparator<T extends Comparable<T>> implements Comparator<Comparable<T>>, Serializable {

        /**
         * Serializable version identifier
         */
        private static final long serialVersionUID = -3852193713161395148L;

        /**
         * Compare the two {@link Comparable Comparable} arguments.
         * This method is equivalent to:
         * <pre>(({@link Comparable Comparable})o1).{@link Comparable#compareTo compareTo}(o2)</pre>
         *
         * @param o1 the first object
         * @param o2 the second object
         * @return result of comparison
         * @throws NullPointerException when <i>o1</i> is <code>null</code>,
         *                              or when <code>((Comparable)o1).compareTo(o2)</code> does
         * @throws ClassCastException   when <i>o1</i> is not a {@link Comparable Comparable},
         *                              or when <code>((Comparable)o1).compareTo(o2)</code> does
         */
        @SuppressWarnings("unchecked") // cast to (T) may throw ClassCastException, see Javadoc
        public int compare(Comparable<T> o1, Comparable<T> o2) {
            return o1.compareTo((T) o2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result +
                ((freqTable == null) ? 0 : freqTable.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Frequency)) {
            return false;
        }
        Frequency other = (Frequency) obj;
        if (freqTable == null) {
            if (other.freqTable != null) {
                return false;
            }
        } else if (!freqTable.equals(other.freqTable)) {
            return false;
        }
        return true;
    }

}
