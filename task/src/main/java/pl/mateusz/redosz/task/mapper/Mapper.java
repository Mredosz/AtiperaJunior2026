package pl.mateusz.redosz.task.mapper;

import pl.mateusz.redosz.task.model.BranchDto;
import pl.mateusz.redosz.task.model.FullBranchDto;
import pl.mateusz.redosz.task.model.FullRepositoryDto;
import pl.mateusz.redosz.task.model.RepositoryDto;

import java.util.List;

public class Mapper {
    private Mapper() {
    }

    public static FullRepositoryDto map(RepositoryDto repositoryDto, List<BranchDto> branches) {
        return FullRepositoryDto
                .builder()
                .name(repositoryDto.name())
                .owner(repositoryDto.owner().login())
                .branches(branches.stream().map(Mapper::mapToBranch).toList())
                .build();
    }

    private static FullBranchDto mapToBranch(BranchDto branchDto) {
        return FullBranchDto
                .builder()
                .name(branchDto.name())
                .sha(branchDto.commit().sha())
                .build();
    }
}
