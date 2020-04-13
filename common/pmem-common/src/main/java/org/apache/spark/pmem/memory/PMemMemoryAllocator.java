package org.apache.spark.pmem.memory;

import org.apache.spark.pmem.PersistentMemoryPlatform;
import org.apache.spark.unsafe.memory.MemoryAllocator;
import org.apache.spark.unsafe.memory.MemoryBlock;

public class PMemMemoryAllocator implements MemoryAllocator {

    @Override
    public MemoryBlock allocate(long size) throws OutOfMemoryError {
        long address = PersistentMemoryPlatform.allocateMemory(size);
        MemoryBlock memoryBlock = new MemoryBlock(null, address, size);
        return memoryBlock;
    }

    @Override
    public void free(MemoryBlock memory) {
        assert (memory.getBaseObject() == null) :
                "Fail to free the memory block by PMem Memory Allocator";
        PersistentMemoryPlatform.freeMemory(memory.getBaseOffset());
    }
}
