import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Wall implements Structure {
    private List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    public Optional<Block> findBlockByColor(String color) {
        Optional<Block> sortedBlock = blocks.stream()
                .filter(block -> color.equals(block.getColor()))
                .findFirst();

        if(sortedBlock.isPresent())
            return sortedBlock;

        sortedBlock = blocks.stream()
                .filter(block -> block instanceof CompositeBlock)
                .map(block -> (CompositeBlock) block)
                .flatMap( block -> block.getBlocks().stream())
                .filter(block -> color.equals(block.getColor()))
                .findFirst();

        return sortedBlock;
    }

    public List<Block> findBlocksByMaterial(String material) {
        List<Block> blockList = blocks.stream()
                .filter(block -> material.equals(block.getMaterial()))
                .collect(Collectors.toList());

        List<Block> compositeBocksList = blocks.stream()
                .filter(block -> block instanceof CompositeBlock)
                .map(block -> (CompositeBlock) block)
                .flatMap( block -> block.getBlocks().stream())
                .filter(block -> material.equals(block.getMaterial()))
                .collect(Collectors.toList());

        blockList.addAll(compositeBocksList);
        return blockList;
    }
    public int count() {
        int blocksQuantity = 0;
        for (Block block : blocks){
            if (block instanceof CompositeBlock) {
                    blocksQuantity = blocksQuantity + ((CompositeBlock) block).getBlocks().size();
            } else {
                blocksQuantity = blocksQuantity + 1;
            }
        }
        return blocksQuantity;
    }
}